package com.example.services;

import com.example.models.Weather;
import com.example.utils.WeatherAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    @Autowired
    private WeatherAPI weatherAPI;

    public Weather getWeather(String city) {
        return weatherAPI.getWeather(city);
    }
}