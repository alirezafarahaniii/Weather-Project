package com.project.server2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitMQProducer implements RabbitTemplate.ConfirmCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    private static final int MAX_RETRY_ATTEMPTS = 3;

    @Value("${rabbitmq.exchange.response.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final Notify notify;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(Notify notify,RabbitTemplate rabbitTemplate) {
        this.notify = notify;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMandatory(true); // Required for publisher confirms
        this.rabbitTemplate.setConfirmCallback(this);
    }

    @Retryable(maxAttempts = MAX_RETRY_ATTEMPTS, backoff = @Backoff(delay = 1000))
    // Retry 3 times with a 1-second delay between attempts
    public void sendMessage(String message) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        LOGGER.info("Sending message: " + message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);

    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            LOGGER.info("Message with correlation ID {} was acknowledged", correlationData.getId());
        } else {
            LOGGER.error("Message with correlation ID {} was not acknowledged: {}", correlationData.getId(), cause);
            // Handle nack, e.g., log the error, requeue the message, or perform error-specific actions
            notify.sendEmailForNotifyAdmin("RabbitMQ message not acknowledged", "Message ID: " + correlationData.getId() + ", Cause: " + cause);
        }
    }
}
