package com.example.komuroshun.androidtabui.data.repository

import com.example.komuroshun.androidtabui.data.source.remote.WeatherInfo

import com.example.komuroshun.androidtabui.data.source.remote.api.Api
import com.example.komuroshun.androidtabui.data.source.remote.api.WeatherLivedoorApiService
import io.reactivex.Observable

class WeatherInfoRepository {
    private var weatherLivedoorApiService: WeatherLivedoorApiService? = null
    companion object {
        fun create(): WeatherInfoRepository = WeatherInfoRepository()
    }
    //TODO Does it have meaning todo with init method ?
    init {
        weatherLivedoorApiService = Api.newInstance().weatherLivedoorApiService
    }

    //TODO Use Single RxJava class ?
    fun getWeatherInfoByCityId(cityId: Int): Observable<WeatherInfo> {
        return weatherLivedoorApiService!!.weatherInfo(cityId)
    }
}