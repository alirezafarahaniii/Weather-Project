package com.project.server2.service;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WeatherApiClient {

    private final WebClient webClient;

    public WeatherApiClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://weatherapi-com.p.rapidapi.com")
                .defaultHeader("X-RapidAPI-Key", "e8be6f61b1msh9ab0702c6abe0ffp1aa5c3jsn50101242f315")
                .defaultHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .build();
    }

    public Mono<String> fetchWeatherData() {
        return webClient
                .get()
                .uri("/current.json?q=53.1%2C-0.13")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);
    }

}


