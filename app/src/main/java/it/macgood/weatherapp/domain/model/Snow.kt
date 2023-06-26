package it.macgood.weatherapp.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.annotations.SerializedName

data class Snow(
    @SerializedName("1h")
    @JsonIgnore
    val oneHour: Double?,
    @SerializedName("3h")
    val threeHours: Double?,
)
