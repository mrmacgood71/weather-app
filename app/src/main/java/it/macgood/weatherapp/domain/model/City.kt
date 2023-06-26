package it.macgood.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class City(
    val id: Int,
    val name: String,
    @SerializedName("coord")
    val coordinates: Coordinates,
    val country: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)