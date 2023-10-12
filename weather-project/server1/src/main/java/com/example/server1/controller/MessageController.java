package com.example.server1.controller;

import com.example.server1.dto.LocationDto;
import com.example.server1.dto.ResquestBody;
import com.example.server1.entity.types.CrudType;
import com.example.server1.entity.types.ErrorCodeList;
import com.example.server1.exception.ResponseException;
import com.example.server1.service.RabbitMQProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

/*
this class handles api call from client
base url is : http://localhost:9090/api/v1/
 */
@RestController
@RequestMapping("/api/v1")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    private RabbitMQProducer producer;

    public MessageController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    //http://localhost:9090/api/v1/getWeather/tehran
    @GetMapping("/getWeather/{city}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Object sendMessageForGeetingWeatherOfCity(Authentication authentication, @PathVariable("city") String city) {
        LOGGER.info(String.format("this user asked for getting weather -> %s", authentication.getPrincipal()));
        LOGGER.info(String.format("send this message to server2 for getting weather of : -> %s", city));
        User user = (User) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        try {
            ResquestBody resquestBody = ResquestBody.builder().body(null).path("/getWeather/" + city).type("Get").user_id(user.getUsername()).build();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(resquestBody);
            LOGGER.info(String.format("send this jsonString to server2 for getting weather of : -> %s", jsonString));
            producer.sendMessage(jsonString);
            return ResponseEntity.ok("Message sent to RabbitMQ ...");
        } catch (Exception e) {
            return new ResponseException(CrudType.READ, user.getUsername(), "FAILED_TO_READ_OBJECT", ErrorCodeList.OBJECT_CAN_NOT_READ.getCode());
        }
    }

    @PostMapping("/addCity")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Object sendMessageForAddingNewCiry(Authentication authentication, @RequestBody LocationDto location) {
        LOGGER.info(String.format("this user asked for getting weather -> %s", authentication.getPrincipal()));
        User user = (User) SecurityContextHolder.getContextHolderStrategy().getContext().getAuthentication().getPrincipal();
        try {
            LOGGER.info(String.format("send this message to server2 for location : -> %s", location));
            // Create an instance of ObjectMapper
            // Convert the RequestBodyDto object to a JSON string
            ResquestBody resquestBody = ResquestBody.builder().body(location).path("/addCity").type("Post").user_id(user.getUsername()).build();
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(resquestBody);
            producer.sendMessage(jsonString);
            return ResponseEntity.ok("Message sent to RabbitMQ ...");
        } catch (Exception e) {
            return new ResponseException(CrudType.READ, user.getUsername(), "FAILED_TO_READ_OBJECT", ErrorCodeList.OBJECT_CAN_NOT_CREATE.getCode());
        }
    }
}
