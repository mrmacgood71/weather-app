package it.macgood.weatherapp.presentation.ui.forecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import it.macgood.core.extension.viewBinding
import it.macgood.core.network.Resource
import it.macgood.weatherapp.R
import it.macgood.weatherapp.databinding.FragmentForecastBinding

@AndroidEntryPoint
class ForecastFragment : Fragment(R.layout.fragment_forecast) {

    private val binding by viewBinding<FragmentForecastBinding>()
    private val forecastViewModel by viewModels<ForecastViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setupForecast()
        }
    }

    private fun FragmentForecastBinding.setupForecast() {
        val adapter = ForecastAdapter()
        forecastViewModel.forecast.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        adapter.differ.submitList(it.list)
                        binding.shimmerLayout.hideShimmer()
                        binding.shimmerLayout.visibility = View.GONE
                    }
                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    binding.shimmerLayout.hideShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                }
            }
        }
        binding.forecastRecyclerView.adapter = adapter
    }
}