package com.example.weather_app.controller;

import com.example.weather_app.model.GeoLocation;
import com.example.weather_app.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/geo")
public class GeoRestController {

    @Autowired
    private GeoService geoService;

    @GetMapping
    public List<GeoLocation> autocomplete(@RequestParam("query") String query) {
        return geoService.getLocations(query);
    }
}