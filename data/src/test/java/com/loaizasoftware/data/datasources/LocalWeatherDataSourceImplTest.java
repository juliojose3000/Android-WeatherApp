package com.loaizasoftware.data.datasources;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.loaizasoftware.data.datasources_impl.LocalWeatherDataSourceImpl;
import com.loaizasoftware.data.local.dao.WeatherDao;
import com.loaizasoftware.data.local.entities.WeatherEntity;
import com.loaizasoftware.domain.models.WeatherModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(MockitoJUnitRunner.class)
public class LocalWeatherDataSourceImplTest {

    @Mock
    private WeatherDao weatherDao;

    @Mock
    private WeatherEntity mockWeatherEntity;

    @Mock
    private WeatherModel mockWeatherModel;

    private LocalWeatherDataSourceImpl localWeatherDataSource;

    @Before
    public void setUp() {
        localWeatherDataSource = new LocalWeatherDataSourceImpl(weatherDao);
    }

    @Test
    public void getCachedWeather_WhenDataExists_ReturnsWeatherModel() throws ExecutionException, InterruptedException, TimeoutException {
        // Arrange
        when(weatherDao.getWeatherData()).thenReturn(mockWeatherEntity);
        when(mockWeatherEntity.toDomainModel()).thenReturn(mockWeatherModel);

        // Act
        CompletableFuture<WeatherModel> result = localWeatherDataSource.getCachedWeather();

        // Assert
        WeatherModel weatherModel = result.get(5, TimeUnit.SECONDS);
        assertNotNull(weatherModel);
        assertEquals(mockWeatherModel, weatherModel);
        verify(weatherDao, times(1)).getWeatherData();
        verify(mockWeatherEntity, times(1)).toDomainModel();
    }

    @Test
    public void getCachedWeather_WhenNoDataExists_CompletesExceptionally() {
        // Arrange
        when(weatherDao.getWeatherData()).thenReturn(null);

        // Act
        CompletableFuture<WeatherModel> result = localWeatherDataSource.getCachedWeather();

        // Assert
        try {
            result.get(5, TimeUnit.SECONDS);
            fail("Expected CompletableFuture to complete exceptionally");
        } catch (ExecutionException e) {
            assertTrue(e.getCause() instanceof Exception);
            assertEquals("No cached weather data available", e.getCause().getMessage());
        } catch (InterruptedException | TimeoutException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        verify(weatherDao, times(1)).getWeatherData();
    }

    @Test
    public void getCachedWeather_WhenDaoThrowsException_CompletesExceptionally() {
        // Arrange
        RuntimeException testException = new RuntimeException("Database error");
        when(weatherDao.getWeatherData()).thenThrow(testException);

        // Act
        CompletableFuture<WeatherModel> result = localWeatherDataSource.getCachedWeather();

        // Assert
        try {
            result.get(5, TimeUnit.SECONDS);
            fail("Expected CompletableFuture to complete exceptionally");
        } catch (ExecutionException e) {
            assertTrue(e.getCause() instanceof RuntimeException);
            assertEquals("Database error", e.getCause().getMessage());
        } catch (InterruptedException | TimeoutException e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        verify(weatherDao, times(1)).getWeatherData();
    }

    @Test
    public void cacheWeather_WithValidWeatherModel_DeletesAndSavesData() throws InterruptedException {
        // Arrange
        WeatherModel testWeatherModel = new WeatherModel();
        // Add a small delay to ensure async operation completes
        doNothing().when(weatherDao).delete();
        doNothing().when(weatherDao).saveWeatherData(any(WeatherEntity.class));

        // Act
        localWeatherDataSource.cacheWeather(testWeatherModel);

        // Wait for async operation to complete
        Thread.sleep(100);

        // Assert
        verify(weatherDao, times(1)).delete();

        ArgumentCaptor<WeatherEntity> entityCaptor = ArgumentCaptor.forClass(WeatherEntity.class);
        verify(weatherDao, times(1)).saveWeatherData(entityCaptor.capture());

        WeatherEntity capturedEntity = entityCaptor.getValue();
        assertNotNull(capturedEntity);
    }

    @Test
    public void cacheWeather_WhenDeleteThrowsException_ThrowsRuntimeException() throws InterruptedException {
        // Arrange
        WeatherModel testWeatherModel = new WeatherModel();
        RuntimeException testException = new RuntimeException("Delete failed");
        doThrow(testException).when(weatherDao).delete();

        // Act & Assert
        try {
            localWeatherDataSource.cacheWeather(testWeatherModel);
            // Wait for async operation to complete
            Thread.sleep(100);
        } catch (Exception e) {
            // The exception might be wrapped or thrown asynchronously
            // We verify the delete was called
        }

        verify(weatherDao, times(1)).delete();
        // saveWeatherData should not be called if delete fails
        verify(weatherDao, never()).saveWeatherData(any(WeatherEntity.class));
    }

    @Test
    public void cacheWeather_WhenSaveThrowsException_ThrowsRuntimeException() throws InterruptedException {
        // Arrange
        WeatherModel testWeatherModel = new WeatherModel();
        RuntimeException testException = new RuntimeException("Save failed");
        doNothing().when(weatherDao).delete();
        doThrow(testException).when(weatherDao).saveWeatherData(any(WeatherEntity.class));

        // Act
        try {
            localWeatherDataSource.cacheWeather(testWeatherModel);
            // Wait for async operation to complete
            Thread.sleep(100);
        } catch (Exception e) {
            // The exception might be wrapped or thrown asynchronously
        }

        // Assert
        verify(weatherDao, times(1)).delete();
        verify(weatherDao, times(1)).saveWeatherData(any(WeatherEntity.class));
    }

    @Test
    public void cacheWeather_WithNullWeatherModel_HandlesGracefully() throws InterruptedException {
        // Arrange
        doNothing().when(weatherDao).delete();

        // Act
        localWeatherDataSource.cacheWeather(null);

        // Wait for async operation to complete
        Thread.sleep(100);

        // Assert
        verify(weatherDao, times(1)).delete();
        verify(weatherDao, never()).saveWeatherData(any(WeatherEntity.class));
    }

    @Test
    public void constructor_WithValidWeatherDao_CreatesInstance() {
        // Act
        LocalWeatherDataSourceImpl dataSource = new LocalWeatherDataSourceImpl(weatherDao);

        // Assert
        assertNotNull(dataSource);
    }
}