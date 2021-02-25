package br.com.alura.kafka.ecommerce;

import br.com.alura.kafka.ecommerce.domain.Order;
import br.com.alura.kafka.ecommerce.kafka.KafkaProducerService;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var orderProducer = new KafkaProducerService<Order>()) {
            try (var emailProducer = new KafkaProducerService<String>()) {
                for (var i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);
                    var order = new Order(userId, orderId, amount);
                    orderProducer.send("ECOMMERCE_NEW_ORDER", userId, order);

                    var email = "Thank you for your order, we are processing your order.";
                    emailProducer.send("ECOMMERCE_SEND_MAIL", userId, email);
                }
            }
        }
    }
}
