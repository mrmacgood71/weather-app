package it.macgood.weatherapp.presentation.ui.weather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import it.macgood.core.extension.viewBinding
import it.macgood.core.fragment.getDrawable
import it.macgood.core.network.Resource
import it.macgood.weatherapp.R
import it.macgood.weatherapp.databinding.FragmentWeatherBinding
import it.macgood.weatherapp.utils.Constants
import it.macgood.weatherapp.utils.WeatherUtils

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private val binding by viewBinding<FragmentWeatherBinding>()

    private val weatherViewModel by viewModels<WeatherViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupScreen()
            setupNavigation()
        }
    }

    private fun FragmentWeatherBinding.setupScreen() {
        weatherViewModel.weather.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let {
                        cityTextView.text = it.name
                        temperatureTextView.text = "${it.main.temperature.toInt()} °C"
                        humidityTextView.text = "${it.main.humidity} %"
                        pressureTextView.text = "${WeatherUtils.hPaToMmHg(it.main.pressure)} мм рс. ст."
                        windTextView.text = "${it.wind.speed} м/с"
                        val weather = it.weather[0]
                        conditionNameTextView.text = weather.main
                        Glide.with(requireContext())
                            .load(Constants.API_IMAGE_URL + weather.icon + "@4x.png")
                            .error(getDrawable(R.drawable.ic_sun))
                            .into(conditionImageView)

                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun FragmentWeatherBinding.setupNavigation() {
        detailsCardView.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_detailsFragment)
            exitTransition = MaterialElevationScale(false).apply {
                duration = 300
            }

            reenterTransition = MaterialElevationScale(true).apply {
                duration = 300
            }
        }
        forecastCardView.setOnClickListener {
            findNavController().navigate(R.id.action_weatherFragment_to_forecastFragment)
            exitTransition = MaterialElevationScale(false).apply {
                duration = 300
            }

            reenterTransition = MaterialElevationScale(true).apply {
                duration = 300
            }
        }
    }

}