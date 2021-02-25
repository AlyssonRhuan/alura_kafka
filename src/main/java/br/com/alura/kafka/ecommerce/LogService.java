package br.com.alura.kafka.ecommerce;

import br.com.alura.kafka.ecommerce.kafka.KafkaConsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.regex.Pattern;

public class LogService {
    public static void main(String[] args) throws InterruptedException {
        var logService = new LogService();
        try (var consumer = new KafkaConsumerService(LogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"), logService::parse)) {
            consumer.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("--------------------------------------");
        System.out.println("lOG " + record.topic());
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
    }
}
