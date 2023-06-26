package it.macgood.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class ForecastSys(
    @SerializedName("pod")
    val partOfTheDay: String
)