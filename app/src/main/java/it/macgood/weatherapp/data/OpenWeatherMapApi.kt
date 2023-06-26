package it.macgood.weatherapp.data

import it.macgood.core.network.Resource
import it.macgood.weatherapp.utils.Constants.API_CITY_ID
import it.macgood.weatherapp.utils.Constants.API_KEY
import it.macgood.weatherapp.utils.Constants.API_LANGUAGE
import it.macgood.weatherapp.utils.Constants.API_UNITS
import it.macgood.weatherapp.domain.model.CurrentWeather
import it.macgood.weatherapp.domain.model.Forecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherMapApi {
    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("id") id: String = API_CITY_ID,
        @Query("appid") appId: String = API_KEY,
        @Query("units") units: String = API_UNITS,
        @Query("lang") lang: String = API_LANGUAGE
    ) : Response<CurrentWeather>

    @GET("/data/2.5/forecast")
    suspend fun getForecast(
        @Query("id") id: String = API_CITY_ID,
        @Query("appid") appId: String = API_KEY,
        @Query("units") units: String = API_UNITS,
        @Query("lang") lang: String = API_LANGUAGE
    ) : Response<Forecast>
}