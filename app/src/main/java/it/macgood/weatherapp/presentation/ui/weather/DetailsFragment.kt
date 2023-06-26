package it.macgood.weatherapp.presentation.ui.weather

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import it.macgood.core.extension.viewBinding
import it.macgood.core.fragment.getColor
import it.macgood.core.fragment.getDrawable
import it.macgood.core.fragment.makeSnackbar
import it.macgood.core.network.Resource
import it.macgood.weatherapp.R
import it.macgood.weatherapp.databinding.FragmentDetailsBinding
import it.macgood.weatherapp.domain.model.CurrentWeather
import it.macgood.weatherapp.utils.Constants
import it.macgood.weatherapp.utils.WeatherUtils
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val binding by viewBinding<FragmentDetailsBinding>()
    private val weatherViewModel by viewModels<WeatherViewModel>()

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transformation = MaterialContainerTransform()
        transformation.interpolator = AnimationUtils.LINEAR_INTERPOLATOR
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragment_container
            duration = 500
            scrimColor = Color.TRANSPARENT
        }

        with(binding) {
            setupDetails()
        }
    }

    private fun FragmentDetailsBinding.setupDetails() {
        weatherViewModel.weather.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { data ->
                        val weather = data.weather[0]
                        val main = data.main

                        cityTextView.text = data.name
                        temperatureTextView.text = "${main.temperature.toInt()} °C"
                        temperatureFeelsLikeTextView.text =
                            "Ощущается как ${main.feelsLike.toInt()} °C"
                        weatherConditionDescriptionTextView.text = weather.description
                        Glide.with(requireContext())
                            .load(Constants.API_IMAGE_URL + weather.icon + "@4x.png")
                            .error(getDrawable(R.drawable.ic_sun))
                            .into(conditionImageView)
                        conditionNameTextView.text = weather.main
                        humidityTextView.text = "${main.humidity} %"
                        pressureTextView.text =
                            "${WeatherUtils.hPaToMmHg(main.pressure)} мм рс. ст."
                        windTextView.text = "${data.wind.speed} м/с"
                        seaLevelTextView.text =
                            "${WeatherUtils.hPaToMmHg(main.seaLevel)} мм рс. ст."
                        groundLevelTextView.text =
                            "${WeatherUtils.hPaToMmHg(main.groundLevel)} мм рс. ст."
                        cloudTextView.text = "${data.clouds.all} %"
                        visibilityTextView.text = "${data.visibility / 1000} км."
                        if (data.rain != null) {
                            rainImageView.visibility = View.VISIBLE
                            rainTextView.visibility = View.VISIBLE
                            rainTextView.text = "${data.rain.oneHour} мм."
                        }
                        if (data.snow != null) {
                            snowImageView.visibility = View.VISIBLE
                            snowTextView.visibility = View.VISIBLE
                            snowTextView.text = "${data.snow.oneHour} мм."
                        }

                        val sunriseTime = convertTimestampToFormattedDate(
                            data.sys.sunrise.toLong(),
                            data.timezone
                        )
                        val sunsetTime = convertTimestampToFormattedDate(
                            data.sys.sunset.toLong(),
                            data.timezone
                        )

                        sunriseTimeTextView.text = sunriseTime
                        sunsetTimeTextView.text = sunsetTime
                        setupChart(data)
                    }
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }
    }

    private fun convertTimestampToFormattedDate(timestamp: Long, timeZoneOffset: Int): String {
        val timestampInMillis = timestamp * 1000
        val date = Date(timestampInMillis)
        val format = SimpleDateFormat("HH:mm dd/MM/yyyy")
        format.timeZone.rawOffset = timeZoneOffset * 1000
        return format.format(date)
    }

//        val myDate = "2014/10/29 18:10:45"
//        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
//        val date = sdf.parse(myDate)
//        val millis = date.time

    var minX = -10f
    var maxX = 10f
    var step = 0.5f

    private fun FragmentDetailsBinding.setupChart(data: CurrentWeather) {
        val entries = arrayListOf<Entry>()

        val sunrise = data.sys.sunrise
        val sunset = data.sys.sunset
        val currentTime = data.dateAndTimeMillis
        val dayTime = sunset - sunrise
        val timeStep = dayTime / 41
        var current = currentTime
        var currentFull = dayTime
        var iconTimes = 0

        var x = minX
        while (x <= maxX) {
            val y = -1 * x * x
            currentFull -= timeStep
            if (currentFull <= current) {
                if (iconTimes == 0) {
                    val element = Entry(x, y)
                    val drawable = getDrawable(R.drawable.ic_sun_indicator)
                    val bitmap = (drawable as BitmapDrawable).bitmap
                    val d: Drawable =
                        BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 47, 47, true))
                    element.icon = d
                    entries.add(element)
                    iconTimes++
                }
                val element = Entry(x, y)
                element.icon = null
                entries.add(element)
            }else {
                val element = Entry(x, y)
                element.icon = null
                entries.add(element)
            }
            x += step
        }

        val dataSet = LineDataSet(entries, "")
        dataSet.lineWidth = 2f
        dataSet.setDrawCircles(false)
        dataSet.setDrawVerticalHighlightIndicator(false)
        dataSet.setDrawHorizontalHighlightIndicator(false)
        dataSet.setDrawHighlightIndicators(false)
        dataSet.color = getColor(R.color.primaryVariant)
        dataSet.fillColor = Color.TRANSPARENT

        val lineData = LineData(dataSet)
        lineData.setDrawValues(false)

        sunSetRiseChart.data = lineData
        sunSetRiseChart.xAxis.isEnabled = false
        sunSetRiseChart.axisLeft.isEnabled = false
        sunSetRiseChart.axisRight.isEnabled = false

        sunSetRiseChart.axisLeft.setDrawGridLines(false)
        sunSetRiseChart.axisRight.setDrawGridLines(false)
        sunSetRiseChart.xAxis.setDrawGridLines(false)
        sunSetRiseChart.legend.isEnabled = false
        sunSetRiseChart.description.isEnabled = false
        sunSetRiseChart.setDrawMarkers(false)

        sunSetRiseChart.invalidate()
    }
}