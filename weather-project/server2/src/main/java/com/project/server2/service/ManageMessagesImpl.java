package com.project.server2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.server2.dto.RequestBodyDto;
import com.project.server2.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ManageMessagesImpl implements ManageMessages, WeatherOfLocations {
    private final RabbitMQProducer producer;
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageMessagesImpl.class);

    public ManageMessagesImpl(RabbitMQProducer producer) {
        this.producer = producer;
    }

    @Override
    public void messageHandler(String message) {
        // Log the received message
        LOGGER.info("Received message: " + message);

        // Parse the incoming message
        RequestBodyDto requestBody = parseMessage(message);

        // Check the message type and execute the appropriate action
        if (requestBody.getType().equals("Get")) {
            handleGetRequest(requestBody);
        } else if (requestBody.getType().equals("Post")) {
            handlePostRequest(requestBody);
        }
    }

    @Override
    public void getWeatherOfSpecificLocation(RequestBodyDto requestBodyDto) {
        WeatherApiClient weatherApiClient = new WeatherApiClient();
        Mono<String> responseMono = weatherApiClient.fetchWeatherData();

        responseMono.subscribe(
                response -> {
                    // Log the received weather data
                    LOGGER.info("Received weather data: " + response);
                    producer.sendMessage(response);
                },
                error -> LOGGER.error("Error fetching weather data: " + error.getMessage())
        );
    }

    private RequestBodyDto parseMessage(String message) {
        try {
            // Parse the JSON message into a RequestBodyDto
            return new ObjectMapper().readValue(message, RequestBodyDto.class);
        } catch (JsonProcessingException e) {
            // If parsing fails, throw a runtime exception
            throw new RuntimeException(e);
        }
    }

    private void handleGetRequest(RequestBodyDto requestBody) {
        // Log that the message is of type GET
        LOGGER.info("Type of message is GET");
        // Handle the GET request
        getWeatherOfSpecificLocation(requestBody);
    }

    private void handlePostRequest(RequestBodyDto requestBody) {
        // Log that the message is of type POST
        LOGGER.info("Type of message is POST");
        // Send a response for the POST request
        sendCityAddedResponse(requestBody.getUser_id());
    }

    private void sendCityAddedResponse(String userId) {
        // Create a response message
        ResponseDto responseDto = ResponseDto.builder()
                .user_id(userId)
                .message("City Has Been added")
                .build();

        String jsonString;
        try {
            // Convert the response message to a JSON string
            jsonString = new ObjectMapper().writeValueAsString(responseDto);
            // Send the JSON response message
            producer.sendMessage(jsonString);
        } catch (JsonProcessingException e) {
            // If JSON serialization fails, throw a runtime exception
            throw new RuntimeException(e);
        }
    }
}
