package com.loaizasoftware.data.datasources;

import com.loaizasoftware.data.datasources_impl.RemoteWeatherDataSourceImpl;
import com.loaizasoftware.data.models.WeatherResponseDTO;
import com.loaizasoftware.data.network.WeatherApiService;
import com.loaizasoftware.domain.models.WeatherModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RemoteWeatherDataSourceImplTest {

    private WeatherApiService mockApiService;
    private RemoteWeatherDataSourceImpl dataSource;
    private Call<WeatherResponseDTO> mockCall;

    @Before
    public void setUp() {
        mockApiService = mock(WeatherApiService.class);
        mockCall = mock(Call.class);
        dataSource = new RemoteWeatherDataSourceImpl(mockApiService);
    }

    @Test
    public void fetchWeather_successfulResponse_returnsWeatherModel() throws Exception {
        // Given
        double lat = 9.0;
        double lon = -83.0;

        WeatherResponseDTO mockDto = mock(WeatherResponseDTO.class);
        WeatherModel expectedModel = new WeatherModel(); // Could be a mock or real instance
        when(mockDto.toDomainModel()).thenReturn(expectedModel);

        Response<WeatherResponseDTO> response = Response.success(mockDto);

        when(mockApiService.getCurrentWeatherByCoords(anyDouble(), anyDouble(), anyString(), anyString()))
                .thenReturn(mockCall);

        ArgumentCaptor<Callback<WeatherResponseDTO>> callbackCaptor = ArgumentCaptor.forClass(Callback.class);

        // When
        CompletableFuture<WeatherModel> future = dataSource.fetchWeather(lat, lon);
        verify(mockCall).enqueue(callbackCaptor.capture());

        // Simulate network response
        callbackCaptor.getValue().onResponse(mockCall, response);

        // Then
        WeatherModel result = future.get(2, TimeUnit.SECONDS);
        assertEquals(expectedModel, result);
    }

    @Test
    public void fetchWeather_apiFailure_triggersExceptionally() {
        // Given
        double lat = 9.0;
        double lon = -83.0;
        Throwable failure = new RuntimeException("Network error");

        when(mockApiService.getCurrentWeatherByCoords(anyDouble(), anyDouble(), anyString(), anyString()))
                .thenReturn(mockCall);

        ArgumentCaptor<Callback<WeatherResponseDTO>> callbackCaptor = ArgumentCaptor.forClass(Callback.class);

        // When
        CompletableFuture<WeatherModel> future = dataSource.fetchWeather(lat, lon);
        verify(mockCall).enqueue(callbackCaptor.capture());

        // Simulate failure
        callbackCaptor.getValue().onFailure(mockCall, failure);

        // Then
        try {
            future.get(2, TimeUnit.SECONDS);
            fail("Expected an exception to be thrown");
        } catch (Exception ex) {
            assertTrue(ex.getCause().getMessage().contains("Network error"));
        }
    }

    @Test
    public void fetchWeather_nullBody_triggersExceptionally() {
        // Given
        double lat = 9.0;
        double lon = -83.0;

        Response<WeatherResponseDTO> response = Response.success(null);

        when(mockApiService.getCurrentWeatherByCoords(anyDouble(), anyDouble(), anyString(), anyString()))
                .thenReturn(mockCall);

        ArgumentCaptor<Callback<WeatherResponseDTO>> callbackCaptor = ArgumentCaptor.forClass(Callback.class);

        // When
        CompletableFuture<WeatherModel> future = dataSource.fetchWeather(lat, lon);
        verify(mockCall).enqueue(callbackCaptor.capture());

        // Simulate response with null body
        callbackCaptor.getValue().onResponse(mockCall, response);

        // Then
        try {
            future.get(2, TimeUnit.SECONDS);
            fail("Expected an exception to be thrown");
        } catch (Exception ex) {
            assertTrue(ex.getCause().getMessage().contains("API error"));
        }
    }
}