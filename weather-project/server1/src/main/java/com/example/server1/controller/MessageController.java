package com.example.server1.controller;

import com.example.server1.dto.LocationDto;
import com.example.server1.dto.RequestBodyDto;
import com.example.server1.entity.types.CrudType;
import com.example.server1.entity.types.ErrorCodeList;
import com.example.server1.exception.ResponseException;
import com.example.server1.service.RabbitMQProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

// Import statements...

@RestController
@RequestMapping("/api/v1")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    private final RabbitMQProducer producer;

    public MessageController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/getWeather/{city}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> getWeatherForCity(
            Authentication authentication,
            @PathVariable String city
    ) {
        User user = (User) authentication.getPrincipal();
        LOGGER.info("User '{}' requested weather for city '{}'", user.getUsername(), city);

        try {
            RequestBodyDto requestBody = RequestBodyDto.builder()
                    .body(null)
                    .path("/getWeather/" + city)
                    .type("Get")
                    .user_id(user.getUsername())
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestBodyJson = objectMapper.writeValueAsString(requestBody);
            LOGGER.info("Sending message to server2 for weather of '{}': {}", city, requestBodyJson);
            producer.sendMessage(requestBodyJson);
            return ResponseEntity.ok("Message sent to RabbitMQ...");
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseException(
                            CrudType.READ,
                            user.getUsername(),
                            "FAILED_TO_READ_OBJECT",
                            ErrorCodeList.OBJECT_CAN_NOT_READ.getCode()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @PostMapping("/addCity")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Object> addCity(
            Authentication authentication,
            @RequestBody LocationDto location
    ) {
        User user = (User) authentication.getPrincipal();
        LOGGER.info("User '{}' requested adding a new city: {}", user.getUsername(), location);

        try {
            RequestBodyDto requestBody = RequestBodyDto.builder()
                    .body(location)
                    .path("/addCity")
                    .type("Post")
                    .user_id(user.getUsername())
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String requestBodyJson = objectMapper.writeValueAsString(requestBody);
            LOGGER.info("Sending message to server2 for location: {}", requestBodyJson);
            producer.sendMessage(requestBodyJson);
            return ResponseEntity.ok("Message sent to RabbitMQ...");
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ResponseException(
                            CrudType.READ,
                            user.getUsername(),
                            "FAILED_TO_READ_OBJECT",
                            ErrorCodeList.OBJECT_CAN_NOT_CREATE.getCode()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
