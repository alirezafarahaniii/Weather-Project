package com.example.server1.controller;

import com.example.server1.dto.LocationDto;
import com.example.server1.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class WeatherController {
    private final WeatherService weatherService;
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/addCity")
    public ResponseEntity<Object> addCity(@RequestBody LocationDto location) {
        // Assuming Location is a POJO representing the location for weather data

        weatherService.sendRequestToOtherDervice(location);


        // Return a response to the client indicating that the request is being processed
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Request sent for processing");
    }

    @GetMapping ("/getWeather/{city}")
    public ResponseEntity<Object> getWeather(@PathVariable String city) {
        // Assuming Location is a POJO representing the location for weather data

        // Send request to RabbitMQ to fetch weather data


        // Return a response to the client indicating that the request is being processed
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Request sent for processing");
    }
}
