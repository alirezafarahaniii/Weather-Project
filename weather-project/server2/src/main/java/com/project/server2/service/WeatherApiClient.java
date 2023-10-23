package com.project.server2.service;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WeatherApiClient {
    private final WebClient webClient;

    private static final String BASE_URL = "https://weatherapi-com.p.rapidapi.com";
    private static final String RAPIDAPI_KEY = "e8be6f61b1msh9ab0702c6abe0ffp1aa5c3jsn50101242f315";
    private static final String RAPIDAPI_HOST = "weatherapi-com.p.rapidapi.com";

    public WeatherApiClient() {
        this.webClient = WebClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader("X-RapidAPI-Key", RAPIDAPI_KEY)
                .defaultHeader("X-RapidAPI-Host", RAPIDAPI_HOST)
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


