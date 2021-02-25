import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Application {
    private static final String VALID_TRANSACTIONS_TOPIC = "valid-transactions";
    private static final String SUSPICIOUS_TRANSACTIONS_TOPIC = "suspicious-transactions";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    public static void main(String[] args) {
        String consumerGroup = "reporting-service";
        System.out.println("Consumer is part of consumer group " + consumerGroup);

        Application application = new Application();
        String bootstrapServers = application.getBootstrapServers();
        System.out.println("bootstrapServers " + bootstrapServers);

        Consumer<String, Transaction> kafkaConsumer = application.createKafkaConsumer(bootstrapServers, consumerGroup);
        application.consumeMessages(List.of(SUSPICIOUS_TRANSACTIONS_TOPIC, VALID_TRANSACTIONS_TOPIC), kafkaConsumer);
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

    private void consumeMessages(List<String> topics, Consumer<String, Transaction> kafkaConsumer) {
        kafkaConsumer.subscribe(topics);

        while (true) {
            ConsumerRecords<String, Transaction> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));

            if (consumerRecords.isEmpty()) {
                continue;
            }

            for (ConsumerRecord<String, Transaction> record : consumerRecords) {
                printMetadata(record);
                recordTransactionForReporting(record.topic(), record.value());
            }

            kafkaConsumer.commitAsync();
        }
    }

    private void recordTransactionForReporting(String topic, Transaction transaction) {
        if (topic.equals(SUSPICIOUS_TRANSACTIONS_TOPIC)) {
            System.out.printf("Recording SUSPICIOUS transaction for user %s, amount of " +
                            "$%.2f originating in %s for further investigation%n",
                    transaction.getUser(), transaction.getAmount(), transaction.getTransactionLocation());

        } else if (topic.equals(VALID_TRANSACTIONS_TOPIC)) {
            System.out.printf("Recording TRANSACTION for user %s, amount $%.2f to show it on user's " +
                            "monthly statement%n",
                    transaction.getUser(), transaction.getAmount());
        }
    }

    private void printMetadata(ConsumerRecord<String, Transaction> record) {
        System.out.printf("Received RECORD (key: %s, value: %s, partition: %d, offset: %d%n",
                record.key(), record.value(), record.partition(), record.offset());
    }

}
