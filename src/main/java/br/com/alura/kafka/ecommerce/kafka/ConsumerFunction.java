package br.com.alura.kafka.ecommerce.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction {
    void consume(ConsumerRecord<String, String> record) throws InterruptedException;
}
