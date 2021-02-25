package br.com.alura.kafka.ecommerce;

import br.com.alura.kafka.ecommerce.domain.Order;
import br.com.alura.kafka.ecommerce.kafka.KafkaConsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {
    public static void main(String[] args) throws InterruptedException {
        var fraudService = new FraudDetectorService();
        try (var consumer = new KafkaConsumerService<>(
                FraudDetectorService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                fraudService::parse,
                Order.class)) {
            consumer.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("--------------------------------------");
        System.out.println("Processing new order, checking fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order processed");
    }
}
