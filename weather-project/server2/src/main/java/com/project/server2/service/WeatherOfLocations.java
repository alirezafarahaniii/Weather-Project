package com.project.server2.service;


import com.project.server2.dto.RequestBodyDto;

public interface WeatherOfLocations {
   void getWeatherOfSpecificLocation(RequestBodyDto requestBodyDto);
}
