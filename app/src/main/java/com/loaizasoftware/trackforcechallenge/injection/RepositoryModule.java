package com.loaizasoftware.trackforcechallenge.injection;


import com.loaizasoftware.data.repositories_impl.WeatherRepositoryImpl;
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

}
