package it.macgood.weatherapp.domain.model

import com.google.gson.annotations.SerializedName

data class ForecastData(
    @SerializedName("dt")
    val dateAndTimeMillis: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    @SerializedName("pop")
    val probabilityOfRain: Double,
    val sys: ForecastSys,
    @SerializedName("dt_txt")
    val dateAndTimeUtc: String,
    val rain: RainPeriod?,
    val snow: Snow?
) {
    @Transient
    val hiddenSnow: Snow? = snow
}