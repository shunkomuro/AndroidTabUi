package com.example.komuroshun.androidtabui.data.repository

import android.util.Log
import com.example.komuroshun.androidtabui.data.source.remote.WeatherInfo

import com.example.komuroshun.androidtabui.data.source.remote.api.Api
import com.example.komuroshun.androidtabui.data.source.remote.api.MyApiService
import io.reactivex.Observable

class WeatherInfoRepository {
    private var myApiService: MyApiService? = null;
    companion object {
        fun create(): WeatherInfoRepository = WeatherInfoRepository()
    }
    //TODO Does it have meaning todo with init method ?
    init {
        myApiService = Api.newInstance().myApiService
    }

    //TODO Use Single RxJava class ?
    fun getWeatherInfoByCityId(cityId: Int): Observable<WeatherInfo> {
        return myApiService!!.weatherInfo(cityId)
    }
}