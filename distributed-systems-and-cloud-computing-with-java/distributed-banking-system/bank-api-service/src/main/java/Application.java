import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Application {
    private static final String SUSPICIOUS_TRANSACTIONS_TOPIC = "suspicious-transactions";
    private static final String VALID_TRANSACTIONS_TOPIC = "valid-transactions";

    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    public static void main(String[] args) {
        Application application = new Application();

        String bootstrapServers = application.getBootstrapServers();
        System.out.println("bootstrapServers " + bootstrapServers);
        Producer<String, Transaction> kafkaProducer = application.createKafkaProducer(bootstrapServers);

        try {
            application.processTransactions(new IncomingTransactionsReader(), new UserResidenceDatabase(), kafkaProducer);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            kafkaProducer.flush();
            kafkaProducer.close();
        }
    }

    private String getBootstrapServers() {
        String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
        return Objects.requireNonNullElse(bootstrapServers, BOOTSTRAP_SERVERS);
    }

    private Producer<String, Transaction> createKafkaProducer(String bootstrapServers) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "banking-api-service");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Transaction.TransactionSerializer.class.getName());
        return new KafkaProducer<>(properties);
    }

    private void processTransactions(
            IncomingTransactionsReader incomingTransactionsReader,
            UserResidenceDatabase userResidenceDatabase,
            Producer<String, Transaction> kafkaProducer
    ) throws ExecutionException, InterruptedException {
        while (incomingTransactionsReader.hasNext()) {
            Transaction transaction = incomingTransactionsReader.next();

            ProducerRecord<String, Transaction> record = processTransaction(transaction, userResidenceDatabase);
            RecordMetadata recordMetadata = kafkaProducer.send(record).get();

            printMetadata(record, recordMetadata);
        }
    }

    private ProducerRecord<String, Transaction> processTransaction(
            Transaction transaction,
            UserResidenceDatabase userResidenceDatabase
    ) {
        if (isSafeTransaction(transaction, userResidenceDatabase)) {
            return getValidTransactionRecord(transaction);
        }
        return getSuspiciousRecord(transaction);
    }

    private boolean isSafeTransaction(Transaction transaction, UserResidenceDatabase userResidenceDatabase) {
        String userResidence = userResidenceDatabase.getUserResidence(transaction.getUser());
        String transactionLocation = transaction.getTransactionLocation();
        return userResidence.equalsIgnoreCase(transactionLocation);
    }

    private ProducerRecord<String, Transaction> getValidTransactionRecord(Transaction transaction) {
        return new ProducerRecord<>(VALID_TRANSACTIONS_TOPIC, transaction.getUser(), transaction);
    }

    private ProducerRecord<String, Transaction> getSuspiciousRecord(Transaction transaction) {
        return new ProducerRecord<>(SUSPICIOUS_TRANSACTIONS_TOPIC, transaction.getUser(), transaction);
    }

    private void printMetadata(ProducerRecord<String, Transaction> record, RecordMetadata recordMetadata) {
        System.out.printf("Record with (key: %s, value: %s), was sent to (partition: %d, offset: %d%n",
                record.key(), record.value(), recordMetadata.partition(), recordMetadata.offset());

        System.out.printf("Transaction (%s) for user %s, in the amount of $%.2f%n",
                record.topic(), record.value().getUser(), record.value().getAmount());
    }
}
