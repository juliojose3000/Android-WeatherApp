package com.loaizasoftware.domain.models;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class WeatherModelTest {

    private WeatherModel weatherModel;

    @Before
    public void setUp() {
        weatherModel = ProvideWeather.getMock();
        weatherModel.main.temp = 70.05;
        weatherModel.main.temp_min = 67.1;
        weatherModel.main.temp_max = 75.9;
        weatherModel.main.feels_like = 72.0;
        weatherModel.main.pressure = 1018;
        weatherModel.main.humidity = 73;
    }

    @Test
    public void testValuesFormatted() {
        assertEquals("70°", weatherModel.main.getFormattedTemp());
        assertEquals("Max 76° / Min 67°", weatherModel.main.getFormattedTempMinAndMax());
        assertEquals("Feels like 72°", weatherModel.main.getFormattedFeelsLike());
        assertEquals("Pressure: 1018 hPa", weatherModel.main.getFormattedPressure());
        assertEquals("Humidity: 73%", weatherModel.main.getFormattedHumidity());
    }

}
