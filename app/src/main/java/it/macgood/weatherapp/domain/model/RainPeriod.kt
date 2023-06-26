package it.macgood.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class RainPeriod(
    @SerializedName("1h")
    val oneHour: Double?,
    @SerializedName("3h")
    val threeHours: Double?
)