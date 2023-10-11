package com.example.server1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final int maxRetryAttempts = 3;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Retryable(maxAttempts = maxRetryAttempts, backoff = @Backoff(delay = 1000)) // Retry 3 times with a 1-second delay between attempts
    public void sendMessage(String message){
        try {
            LOGGER.info(String.format("Message sent -> %s", message));
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
        } catch (Exception e) {
            LOGGER.error("Error sending message: " + e.getMessage());
            // You can handle or log the error here, and the method will be retried
            throw e; // Re-throw the exception to trigger the retry
        }

    }
}
