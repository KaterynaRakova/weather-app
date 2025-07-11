package com.example.weather_app.service;

import com.example.weather_app.model.GeoLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class GeoService {

    @Value("${weather.api.key}")
    private String apiKey;
    @Value("${geocoding.api.url}")
    private String geoUrl;
    private final RestTemplate restTemplate = new RestTemplate();

    public List<GeoLocation> getLocations(String city) {
        String url = geoUrl + city + "&limit=5&appid=" + apiKey;
        String json = restTemplate.getForObject(url, String.class);
        Type listType = new TypeToken<List<GeoLocation>>() {}.getType();
        return new Gson().fromJson(json, listType);
    }
}