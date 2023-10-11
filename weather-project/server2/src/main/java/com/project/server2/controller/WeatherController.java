package com.project.server2.controller;


import com.project.server2.dto.LocationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class WeatherController {


    @PostMapping("/getWeather")
    public ResponseEntity<Object> getWeather(@RequestBody LocationDto location) {
        // Assuming Location is a POJO representing the location for weather data

        // Send request to RabbitMQ to fetch weather data


        // Return a response to the client indicating that the request is being processed
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Request sent for processing");
    }
}
