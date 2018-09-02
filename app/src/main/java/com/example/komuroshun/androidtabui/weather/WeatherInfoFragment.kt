package com.example.komuroshun.androidtabui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.komuroshun.androidtabui.BaseFragment

import com.example.komuroshun.androidtabui.R
import com.example.komuroshun.androidtabui.data.source.remote.api.Api
import com.example.komuroshun.androidtabui.data.source.remote.WeatherInfo
import kotlinx.android.synthetic.main.fragment_weather_info.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cityName = it.getString(ARG_CITY_NAME)
            cityId = Integer.valueOf(it.getString(ARG_CITY_ID))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Api.newInstance().myApiService!!.weatherInfo(cityId).enqueue(object: Callback<WeatherInfo> {
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
                val weatherInfo: WeatherInfo? = response.body()
                if (weatherInfo!!.forecasts.isNotEmpty()) {
                    tvCityName.text = cityName + "の" + weatherInfo!!.forecasts[0].dateLabel + "の天気: "
                    tvWeatherTelop.text = weatherInfo!!.forecasts[0].telop
                }
                tvWeatherDesc.text = weatherInfo!!.description.text
            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
            }
        })
    }

    companion object {
        /**
         * @param cityName Parameter 1.
         * @param cityId Parameter 2.
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
