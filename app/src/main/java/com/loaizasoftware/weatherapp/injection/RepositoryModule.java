package com.loaizasoftware.weatherapp.injection;


import com.loaizasoftware.data.datasources_impl.LocalWeatherDataSourceImpl;
import com.loaizasoftware.data.datasources_impl.RemoteWeatherDataSourceImpl;
import com.loaizasoftware.data.repositories_impl.WeatherRepositoryImpl;
import com.loaizasoftware.domain.datasource.LocalWeatherDataSource;
import com.loaizasoftware.domain.datasource.RemoteWeatherDataSource;
import com.loaizasoftware.domain.repository.WeatherRepository;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract WeatherRepository bindWeatherRepository(WeatherRepositoryImpl weatherRepositoryImpl);

    @Binds
    @Singleton
    public abstract LocalWeatherDataSource bindLocalDataSource(LocalWeatherDataSourceImpl localWeatherDataSourceImpl);

    @Binds
    @Singleton
    public abstract RemoteWeatherDataSource bindRemoteDataSource(RemoteWeatherDataSourceImpl remoteWeatherDataSourceImpl);

}
