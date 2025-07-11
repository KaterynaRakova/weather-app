package com.example.weather_app.service;
import com.example.weather_app.model.WeatherResponse;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;


    private RestTemplate restTemplate=new RestTemplate();
    public WeatherResponse getWeatherByCoordinates(double lat, double lon) {
        try {
            String url = apiUrl + "?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";
            String json = restTemplate.getForObject(url, String.class);
            return new Gson().fromJson(json, WeatherResponse.class);
        } catch (Exception e) {
            System.out.println("Ошибка погоды по координатам: " + e.getMessage());
            return null;
        }
    }
}
