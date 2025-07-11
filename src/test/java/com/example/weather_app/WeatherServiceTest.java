package com.example.weather_app;

import com.example.weather_app.model.WeatherResponse;
import com.example.weather_app.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(weatherService, "apiKey", "fake-key");
        ReflectionTestUtils.setField(weatherService, "apiUrl", "https://api.openweathermap.org/data/2.5/weather");
        ReflectionTestUtils.setField(weatherService, "restTemplate", restTemplate);
    }

    @Test
    public void testGetWeatherByCoordinates_success() {
        String fakeJson = """
            {
              "main": { "temp": 21.0 },
              "weather": [ { "description": "clear sky" } ]
            }
        """;

        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(fakeJson);

        WeatherResponse result = weatherService.getWeatherByCoordinates(50.45, 30.52);

        assertNotNull(result);
        assertEquals(21.0, result.main.temp);
        assertEquals("clear sky", result.weather.get(0).description);
    }

    @Test
    public void testGetWeatherByCoordinates_failure() {
        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenThrow(new RuntimeException("API error"));

        WeatherResponse result = weatherService.getWeatherByCoordinates(0, 0);

        assertNull(result);
    }
}