package com.example.komuroshun.androidtabui.weather

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.komuroshun.androidtabui.BaseFragment

import com.example.komuroshun.androidtabui.R
import com.example.komuroshun.androidtabui.databinding.FragmentWeatherInfoBinding

private const val ARG_CITY_ID = "arg_city_id"
private const val ARG_CITY_NAME = "arg_city_name"

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
            cityId = Integer.valueOf(it.getString(ARG_CITY_ID))
            cityName = it.getString(ARG_CITY_NAME)
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

        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action != KeyEvent.ACTION_DOWN) return@OnKeyListener false
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                mListener!!.onFragmentBack()
                return@OnKeyListener true
            }
            false
        })
        weatherInfoViewModel.getWeatherInfo(cityId, cityName!!)
    }

    companion object {
        /**
         * @param cityId city's primary id.
         * @param cityName city's name.
         * @return A new instance of fragment WeatherInfoFragment.
         */
        @JvmStatic
        fun newInstance(cityName: String, cityId: String) =
                WeatherInfoFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_CITY_ID, cityId)
                        putString(ARG_CITY_NAME, cityName)
                    }
                }
    }
}
