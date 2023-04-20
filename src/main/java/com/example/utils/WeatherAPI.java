package com.example.utils;

import com.example.models.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;

@Component
public class WeatherAPI {
    @Value("${api.key}")
    private String apiKey;

    private static WeatherAPI instance;

    private WeatherAPI() {}

    public static WeatherAPI getInstance() {
        if (instance == null) {
            instance = new WeatherAPI();
        }
        return instance;
    }

    public Weather getWeather(String city) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = new JSONObject(response);
        Weather weather = new Weather();
        weather.setCity(jsonObject.getString("name"));
        JSONArray weatherArray = jsonObject.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);
        weather.setDescription(weatherObject.getString("description"));
        JSONObject mainObject = jsonObject.getJSONObject("main");
        weather.setTemperature(mainObject.getDouble("temp"));
        weather.setFeelsLike(mainObject.getDouble("feels_like"));
        weather.setMinTemperature(mainObject.getDouble("temp_min"));
        weather.setMaxTemperature(mainObject.getDouble("temp_max"));
        weather.setPressure(mainObject.getDouble("pressure"));
        weather.setHumidity(mainObject.getDouble("humidity"));
        return weather;
    }
    
    public List<Weather> getWeatherForecast(String city) throws JsonMappingException, JsonProcessingException {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + apiKey + "&units=metric";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(response.getBody());
        List<Weather> forecast = new ArrayList<>();
        for (JsonNode node : root.get("list")) {
            Weather weather = new Weather();
            weather.setCity(city);
            weather.setDate(node.get("dt_txt").asText());
            weather.setTemperature(node.get("main").get("temp").asDouble());
            weather.setHumidity(node.get("main").get("humidity").asInt());
            weather.setDescription(node.get("weather").get(0).get("description").asText());
            forecast.add(weather);
        }
        return forecast;
    }
    
}