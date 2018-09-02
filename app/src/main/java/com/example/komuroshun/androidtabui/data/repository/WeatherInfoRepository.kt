//package com.example.komuroshun.androidtabui.data.repository
//
//import com.example.komuroshun.androidtabui.data.source.remote.WeatherInfo
//import com.example.komuroshun.androidtabui.data.source.remote.api.Api
//import com.example.komuroshun.androidtabui.data.source.remote.api.MyApiService
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class WeatherInfoRepository {
//    private var myApiService: MyApiService? = null;
//
//    companion object {
//        fun newInstance(): WeatherInfoRepository = WeatherInfoRepository()
//    }
//    init {
//        myApiService = Api.newInstance().myApiService
//    }
//
//    public fun getWeatherInfoByCityId(cityId: Int): WeatherInfo {
//        myApiService!!.weatherInfo(cityId).enqueue(object: Callback<WeatherInfo> {
//            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
////                val weatherInfo: WeatherInfo? = response.body()
////                tvCityName.text = cityName + "の" + weatherInfo!!.forecasts[0].dateLabel + "の天気: "
////                tvWeatherTelop.text = weatherInfo!!.forecasts[0].telop
////                tvWeatherDesc.text = weatherInfo!!.description.text
//            }
//
//            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
//            }
//        })
//    }
//}