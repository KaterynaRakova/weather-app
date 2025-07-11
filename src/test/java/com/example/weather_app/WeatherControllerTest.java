package com.example.weather_app;

import com.example.weather_app.controller.WeatherController;
import com.example.weather_app.model.GeoLocation;
import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.service.GeoService;
import com.example.weather_app.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeoService geoService;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void testSearchCity() throws Exception {
        GeoLocation location = new GeoLocation();
        location.setName("Odessa");
        location.setCountry("UA");
        location.setLat(46.48);
        location.setLon(30.73);

        Mockito.when(geoService.getLocations("Odessa"))
                .thenReturn(List.of(location));

        mockMvc.perform(get("/search").param("city", "Odessa"))
                .andExpect(status().isOk())
                .andExpect(view().name("select_city"))
                .andExpect(model().attributeExists("results"));
    }

    @Test
    public void testGetWeatherByCoordinates() throws Exception {
        WeatherResponse response = new WeatherResponse();
        WeatherResponse.Main main = new WeatherResponse.Main();
        main.temp = 25.0;
        WeatherResponse.Weather weather = new WeatherResponse.Weather();
        weather.description = "clear sky";
        response.main = main;
        response.weather = List.of(weather);

        Mockito.when(weatherService.getWeatherByCoordinates(46.48, 30.73))
                .thenReturn(response);

        mockMvc.perform(get("/weather")
                        .param("lat", "46.48")
                        .param("lon", "30.73")
                        .param("name", "Odessa")
                        .param("country", "UA"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("city", "Odessa, UA"))
                .andExpect(model().attribute("temp", 25.0))
                .andExpect(model().attribute("desc", "clear sky"));
    }
}