package br.com.alura.kafka.ecommerce;

import br.com.alura.kafka.ecommerce.kafka.KafkaConsumerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class EmailService {
    public static void main(String[] args) throws InterruptedException {
        var emailService = new EmailService();
        try (var consumer = new KafkaConsumerService(
                EmailService.class.getSimpleName(),
                "ECOMMERCE_SEND_MAIL",
                emailService::parse,
                String.class,
                new HashMap<>())) {
            consumer.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) throws InterruptedException {
        System.out.println("--------------------------------------");
        System.out.println("Send email");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        Thread.sleep(1000);
        System.out.println("Email send");
    }
}
