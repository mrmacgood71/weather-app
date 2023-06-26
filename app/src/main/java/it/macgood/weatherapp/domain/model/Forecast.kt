package it.macgood.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    val city: City,
    @SerializedName("cnt")
    val count: Int,
    @SerializedName("cod")
    val code: String,
    val list: List<ForecastData>,
    val message: Int
)