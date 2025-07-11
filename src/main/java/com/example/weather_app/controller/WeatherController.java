package com.example.weather_app.controller;

import com.example.weather_app.model.GeoLocation;
import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.service.GeoService;
import com.example.weather_app.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WeatherController {

    @Autowired
    private GeoService geoService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/search")
    public String search(@RequestParam String city, Model model) {
        List<GeoLocation> results = geoService.getLocations(city);

        if (results.isEmpty()) {
            model.addAttribute("error", "City not found. Please, try again.");
            return "index";
        }

        if (results.size() == 1) {
            GeoLocation loc = results.get(0);
            return "redirect:/weather?lat=" + loc.getLat() + "&lon=" + loc.getLon() + "&name=" + loc.getName();
        }

        model.addAttribute("locations", results);
        return "select_city";
    }

    @GetMapping("/weather")
    public String weather(@RequestParam double lat, @RequestParam double lon,
                          @RequestParam String name, @RequestParam String country,
                          Model model) {
        WeatherResponse weather = weatherService.getWeatherByCoordinates(lat, lon);

        if (weather == null || weather.main == null) {
            model.addAttribute("error", "Weather not found.");
            return "weather";
        }

        model.addAttribute("city", name + ", " + country);
        model.addAttribute("temp", weather.main.temp);
        model.addAttribute("desc", weather.weather.get(0).description);
        return "weather";
    }


}