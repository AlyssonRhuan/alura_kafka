package br.com.alura.kafka.ecommerce.kafka;

import br.com.alura.kafka.ecommerce.Serializer.GsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class KafkaConsumerService<T> implements Closeable {
    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;

    public KafkaConsumerService(String groupId, String topic, ConsumerFunction parse, Class<T> type) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties(type, groupId));
        consumer.subscribe(Collections.singletonList(topic));

    }

    public KafkaConsumerService(String groupId, Pattern topic, ConsumerFunction parse, Class<T> type) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties(type, groupId));
        consumer.subscribe(topic);
    }

    public void run() throws InterruptedException {
        while (true) {
            //consumer poll pergunta se há mensagens durante algum tempo
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");
                for (var record : records) {
                    parse.consume(record);
                }
            }
        }
    }

    private Properties properties(Class<T> type, String groupId) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
