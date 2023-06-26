package it.macgood.weatherapp.domain.usecase

import it.macgood.core.network.Resource
import it.macgood.weatherapp.domain.model.Forecast
import it.macgood.weatherapp.domain.repository.WeatherMapRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetForecastUseCase @Inject constructor(
    private val repository: WeatherMapRepository
) {
    suspend fun execute() : Response<Forecast> = repository.getForecast()
}