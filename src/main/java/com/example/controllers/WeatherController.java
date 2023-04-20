package com.example.controllers;

import com.example.models.Weather;
import com.example.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/weather")
    public String weather(@RequestParam String city, Model model) {
        Weather weather = weatherService.getWeather(city);
        model.addAttribute("weather", weather);
        return "weather";
    }
    
    @PostMapping("/forecast")
    public ModelAndView showWeatherForecast(@RequestParam String city) throws JsonMappingException, JsonProcessingException {
        List<Weather> forecast = weatherService.getWeatherForecast(city);
        ModelAndView modelAndView = new ModelAndView("forecast");
        modelAndView.addObject("forecast", forecast);
        return modelAndView;
    }
}