package br.com.alura.kafka.ecommerce;

import br.com.alura.kafka.ecommerce.kafka.KafkaConsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {
    public static void main(String[] args) throws InterruptedException {
        var fraudService = new FraudDetectorService();
        try (var consumer = new KafkaConsumerService(FraudDetectorService.class.getSimpleName(),
                    "ECOMMERCE_NEW_ORDER", fraudService::parse)) {
            consumer.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) throws InterruptedException {
        System.out.println("--------------------------------------");
        System.out.println("Processing new order, checking fraud");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        Thread.sleep(5000);
        System.out.println("Order processed");
    }
}
