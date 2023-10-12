package com.example.server1.service;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    @RabbitListener(queues = {"${rabbitmq.response.queue.name}"})
    public void consume(String message, @Header(AmqpHeaders.CHANNEL) Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag){
        try {
            // Process the message
            LOGGER.info(String.format("Received message -> %s", message));
            // Your processing logic here
        } catch (Exception e) {
            LOGGER.error("Error processing message: " + e.getMessage());
            // Handle the error, e.g., log it, send a notification, or perform error-specific actions

        }
    }
}