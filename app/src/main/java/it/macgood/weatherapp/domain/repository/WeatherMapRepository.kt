package it.macgood.weatherapp.domain.repository

import it.macgood.core.network.Resource
import it.macgood.weatherapp.domain.model.CurrentWeather
import it.macgood.weatherapp.domain.model.Forecast
import retrofit2.Response
import javax.inject.Singleton

@Singleton
interface WeatherMapRepository {

    suspend fun getWeather() : Response<CurrentWeather>

    suspend fun getForecast() : Response<Forecast>
}