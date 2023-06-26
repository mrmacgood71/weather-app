package it.macgood.weatherapp.domain.model

data class Sys(
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)