package it.macgood.weatherapp.presentation.ui.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.macgood.core.network.Resource
import it.macgood.weatherapp.domain.model.Forecast
import it.macgood.weatherapp.domain.usecase.GetForecastUseCase
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {
    val forecast: MutableLiveData<Resource<Forecast>> = MutableLiveData()

    init {
        getForecast()
    }

    private fun getForecast() = viewModelScope.launch {
        forecast.postValue(Resource.Loading())
        val response = getForecastUseCase.execute()
        forecast.postValue(handleForecastResponse(response))
    }

    private fun handleForecastResponse(response: Response<Forecast>): Resource<Forecast>? {
        if (response.isSuccessful) {
            response.body()?.let{
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}