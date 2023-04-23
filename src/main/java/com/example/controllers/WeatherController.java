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

import com.example.models.User;
import com.example.services.UserService;
import com.example.services.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class WeatherController {
    @Autowired
    private WeatherService weatherService;
    
    private final UserService userService;
    
    @Autowired
    public WeatherController() throws SQLException {
        this.userService = UserServiceImpl.createUserServiceImpl();
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    
    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }
    
    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return "redirect:/index";
        } else {
            return "login";
        }
    }
    
    @GetMapping("/index")
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
