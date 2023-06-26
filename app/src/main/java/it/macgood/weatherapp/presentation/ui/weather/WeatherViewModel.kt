package it.macgood.weatherapp.presentation.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.macgood.core.network.Resource
import it.macgood.weatherapp.domain.model.CurrentWeather
import it.macgood.weatherapp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    val weather: MutableLiveData<Resource<CurrentWeather>> = MutableLiveData()

    init {
        getWeather()
    }

    private fun getWeather() = viewModelScope.launch {
        weather.postValue(Resource.Loading())
        val response = getWeatherUseCase.execute()
        weather.postValue(handleWeatherResponse(response))
    }

    private fun handleWeatherResponse(response: Response<CurrentWeather>): Resource<CurrentWeather>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}