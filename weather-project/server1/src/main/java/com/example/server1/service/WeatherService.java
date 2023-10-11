package com.example.server1.service;

import com.example.server1.dto.LocationDto;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private RabbitMQProducer producer;


    public WeatherService(RabbitMQProducer producer) {
        this.producer = producer;
    }

    public void sendRequestToOtherDervice(LocationDto location) {
        producer.sendMessage(String.valueOf(location));
    }
}
