package it.macgood.weatherapp.data.repository

import it.macgood.weatherapp.data.OpenWeatherMapApi
import it.macgood.weatherapp.domain.model.CurrentWeather
import it.macgood.weatherapp.domain.model.Forecast
import it.macgood.weatherapp.domain.repository.WeatherMapRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherMapRepositoryImpl @Inject constructor(
    private val api: OpenWeatherMapApi
) : WeatherMapRepository {

    override suspend fun getWeather(): Response<CurrentWeather> = api.getCurrentWeather()

    override suspend fun getForecast(): Response<Forecast> = api.getForecast()

}