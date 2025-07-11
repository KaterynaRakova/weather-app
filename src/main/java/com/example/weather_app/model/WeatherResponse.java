package com.example.weather_app.model;

import lombok.Data;

import java.util.List;


public class WeatherResponse {

    public Main main;
    public List<Weather> weather;
    public String name;


    public static class Main {
        public double temp;
    }


    public static class Weather {
        public String description;
    }
}