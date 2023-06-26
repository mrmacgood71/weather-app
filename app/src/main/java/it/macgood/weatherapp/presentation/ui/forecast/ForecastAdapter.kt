package it.macgood.weatherapp.presentation.ui.forecast

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.macgood.weatherapp.R
import it.macgood.weatherapp.databinding.FragmentForecastBinding
import it.macgood.weatherapp.databinding.ItemForecastBinding
import it.macgood.weatherapp.domain.model.Forecast
import it.macgood.weatherapp.domain.model.ForecastData
import it.macgood.weatherapp.utils.Constants
import it.macgood.weatherapp.utils.WeatherUtils

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ForecastData>() {
        override fun areItemsTheSame(oldItem: ForecastData, newItem: ForecastData): Boolean {
            return oldItem.dateAndTimeMillis == newItem.dateAndTimeMillis
        }

        override fun areContentsTheSame(oldItem: ForecastData, newItem: ForecastData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ForecastViewHolder(ItemForecastBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecast = differ.currentList[position]

        with(holder) {
            binding.tempTextView.text = "${forecast.main.temperature.toInt()} °C"
            Glide.with(itemView)
                .load(Constants.API_IMAGE_URL + forecast.weather[0].icon + "@4x.png")
                .error(binding.root.resources.getDrawable(R.drawable.ic_condition_failed))
                .into(binding.weatherConditionImageView)
            binding.humidityTextView.text = "${forecast.main.humidity} %"
            binding.pressureTextView.text = "${WeatherUtils.hPaToMmHg(forecast.main.pressure)} мм рс. ст."
            binding.windTextView.text = "${forecast.wind.speed} м/с"
            binding.timeTextView.text = forecast.dateAndTimeUtc
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ForecastViewHolder(val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root)
}