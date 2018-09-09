package com.example.komuroshun.androidtabui.weather

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.komuroshun.androidtabui.BaseFragment

import com.example.komuroshun.androidtabui.R
import com.example.komuroshun.androidtabui.databinding.FragmentWeatherInfoBinding
import com.example.komuroshun.androidtabui.viewmodel.WeatherInfoViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_CITY_NAME = "param1"
private const val ARG_CITY_ID = "param2"

/**
 *  This is view that display weather information detail
 *
 */
class WeatherInfoFragment : BaseFragment() {

    private var cityName: String? = null
    private var cityId: Int = 0
    private val weatherInfoViewModel: WeatherInfoViewModel = WeatherInfoViewModel.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityName = it.getString(ARG_CITY_NAME)
            cityId = Integer.valueOf(it.getString(ARG_CITY_ID))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentWeatherInfoBinding = DataBindingUtil.inflate(inflater
                , R.layout.fragment_weather_info, container, false)
        binding.setViewModel(weatherInfoViewModel)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        weatherInfoViewModel.getWeatherInfo(cityId, cityName!!)
    }

    companion object {
        /**
         * @param cityName city's name.
         * @param cityId city's primary id.
         * @return A new instance of fragment WeatherInfoFragment.
         */
        @JvmStatic
        fun newInstance(cityName: String, cityId: String) =
                WeatherInfoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_CITY_NAME, cityName)
                        putString(ARG_CITY_ID, cityId)
                    }
                }
    }
}
