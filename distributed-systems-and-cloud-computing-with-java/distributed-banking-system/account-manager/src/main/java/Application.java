import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.Properties;

public class Application {
    private static final String VALID_TRANSACTIONS_TOPIC = "valid-transactions";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    public static void main(String[] args) {
        Application application = new Application();

        String consumerGroup = "account-manager";
        System.out.println("Consumer is part of consumer group " + consumerGroup);

        String bootstrapServers = application.getBootstrapServers();
        System.out.println("bootstrapServers " + bootstrapServers);

        Consumer<String, Transaction> kafkaConsumer = application.createKafkaConsumer(bootstrapServers, consumerGroup);
        application.consumeMessages(VALID_TRANSACTIONS_TOPIC, kafkaConsumer);
    }

    private String getBootstrapServers() {
        String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
        return Objects.requireNonNullElse(bootstrapServers, BOOTSTRAP_SERVERS);
    }

    private Consumer<String, Transaction> createKafkaConsumer(String bootstrapServers, String consumerGroup) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Transaction.TransactionDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new KafkaConsumer<>(properties);
    }

    private void consumeMessages(String topic, Consumer<String, Transaction> kafkaConsumer) {
        kafkaConsumer.subscribe(Collections.singletonList(topic));

        while (true) {
            ConsumerRecords<String, Transaction> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));

            if (consumerRecords.isEmpty()) {
                continue;
            }

            for (ConsumerRecord<String, Transaction> record : consumerRecords) {
                approveTransaction(record.value());
                printMetadata(record);
            }

            kafkaConsumer.commitAsync();
        }
    }

    private void approveTransaction(Transaction transaction) {
        System.out.printf("Authorizing transaction for user %s, in the amount of $%.2f%n",
                transaction.getUser(), transaction.getAmount());
    }

    private void printMetadata(ConsumerRecord<String, Transaction> record) {
        System.out.printf("Received record (key: %s, value: %s, partition: %d, offset: %d%n",
                record.key(), record.value(), record.partition(), record.offset());
    }
}
