package com.example.services;

import com.example.models.Weather;
import com.example.utils.WeatherAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    @Autowired
    private WeatherAPI weatherAPI;

    public Weather getWeather(String city) {
        return weatherAPI.getWeather(city);
    }
    
    public List<Weather> getWeatherForecast(String city) throws JsonMappingException, JsonProcessingException {
//        String apiKey = environment.getProperty("api.key");
        return weatherAPI.getWeatherForecast(city);
    }
    
}