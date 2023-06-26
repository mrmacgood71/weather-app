package it.macgood.weatherapp.utils

object WeatherUtils {
    fun hPaToMmHg(pressure: Int): Int {
        return (pressure * 0.75).toInt()
    }
}