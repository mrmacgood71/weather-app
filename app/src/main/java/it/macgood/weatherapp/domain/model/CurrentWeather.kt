package it.macgood.weatherapp.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("coord")
    val coordinates: Coordinates,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    @SerializedName("dt")
    val dateAndTimeMillis: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    @SerializedName("cod")
    val code: Int,
    val clouds: Clouds,
    val rain: RainPeriod?,
    @JsonIgnore
    val snow: Snow?
) {

}