package it.macgood.weatherapp.domain.usecase

import it.macgood.core.network.Resource
import it.macgood.weatherapp.domain.model.CurrentWeather
import it.macgood.weatherapp.domain.repository.WeatherMapRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherMapRepository
) {
    suspend fun execute(): Response<CurrentWeather> {
        return repository.getWeather()
    }
}