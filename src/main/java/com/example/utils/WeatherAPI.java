package com.example.utils;

import com.example.models.Weather;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
}