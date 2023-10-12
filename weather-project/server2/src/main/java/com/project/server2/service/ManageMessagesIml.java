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
public class ManageMessagesIml implements ManageMessages,WeatherOfLocations{
    private RabbitMQProducer producer;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    public ManageMessagesIml(RabbitMQProducer producer) {
        this.producer = producer;
    }
    @Override
    public void messageHandler(String message) {
        LOGGER.info(String.format("message handler resived:  -> %s",message));

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBodyDto body = null;
        // Convert the JSON string to your RequestBodyDto class
        try {
            body  = objectMapper.readValue(message, RequestBodyDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        switch(body.getType())
        {
            case "Get" :
                LOGGER.info(String.format("type of message is GET"));
                getWeatherOfSpecificLocation(body);
                break; // break is optional

            case "Post" :
                LOGGER.info(String.format("type of message is POST"));
                ResponseDto responseDto = ResponseDto.builder().user_id(body.getUser_id()).message("City Has Been added").build();
                objectMapper = new ObjectMapper();
                String jsonString = null;
                try {
                    jsonString = objectMapper.writeValueAsString(responseDto);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                producer.sendMessage(jsonString);
                break; // break is optional
            default :
               return;
        }
    }


    @Override
    public void getWeatherOfSpecificLocation(RequestBodyDto requestBodyDto) {
        WeatherApiClient weatherApiClient = new WeatherApiClient();
        Mono<String> responseMono = weatherApiClient.fetchWeatherData();
        responseMono.subscribe( response -> {System.out.println(response);
                producer.sendMessage(response);
                }, error -> System.err.println("Error: " + error.getMessage())
        );

    }
}
