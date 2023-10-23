package com.project.server2.service;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMQConsumer {
    private final ManageMessages manageMessages;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @Autowired
    public RabbitMQConsumer(ManageMessages manageMessages) {
        this.manageMessages = manageMessages;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consume(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            processMessage(message);
            channel.basicAck(deliveryTag, false); // Acknowledge the message
        } catch (IOException e) {
            handleProcessingError(e, channel, deliveryTag);
        } catch (Exception e) {
            handleProcessingError(e);
        }
    }

    private void processMessage(String message) {
        LOGGER.info("Received message: " + message);
        manageMessages.messageHandler(message);
        // Add your specific processing logic here
    }

    private void handleProcessingError(Exception e) {
        LOGGER.error("Error processing message: " + e.getMessage());
        // Handle the error, e.g., log it, send a notification, or perform error-specific actions
    }

    private void handleProcessingError(Exception e, Channel channel, long deliveryTag) {
        LOGGER.error("Error processing message: " + e.getMessage());
        try {
            // Nack the message and requeue it
            channel.basicNack(deliveryTag, false, true);
        } catch (IOException ex) {
            LOGGER.error("Error during nack: " + ex.getMessage());
            // Handle nack error, e.g., log it, send a notification, or perform error-specific actions
        }
    }
}