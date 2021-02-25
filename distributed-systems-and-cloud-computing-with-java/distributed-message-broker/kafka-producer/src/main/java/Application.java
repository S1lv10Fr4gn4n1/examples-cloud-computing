import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Application {
    private static final String TOPIC = "events";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

    public static void main(String[] args) {
        Application application = new Application();
        String bootstrapServers = application.getBootstrapServers();
        System.out.println("bootstrapServers " + bootstrapServers);
        Producer<Long, String> kafkaProducer = application.createKafkaProducer(bootstrapServers);
        try {
            application.produceMessages(10, kafkaProducer);
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

    private void produceMessages(int numberOfMessages, Producer<Long, String> kafkaProducer)
            throws ExecutionException, InterruptedException {
        for (int key = 0; key < numberOfMessages; key++) {
            String value = String.format("event %d", key);
            ProducerRecord<Long, String> record = new ProducerRecord<>(TOPIC, (long) key, value);
            RecordMetadata recordMetadata = kafkaProducer.send(record).get();

            System.out.printf("Record with (key: %s, value: %s), was sent to (partition: %d, offset: %d%n",
                    record.key(), record.value(), recordMetadata.partition(), recordMetadata.offset());
        }
    }

    private Producer<Long, String> createKafkaProducer(String bootstrapServers) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "events-producer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<>(properties);
    }
}
