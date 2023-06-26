package it.macgood.weatherapp.presentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.macgood.weatherapp.data.repository.WeatherMapRepositoryImpl
import it.macgood.weatherapp.domain.repository.WeatherMapRepository

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun provideWeatherMapRepository(
        repository: WeatherMapRepositoryImpl
    ) : WeatherMapRepository
}