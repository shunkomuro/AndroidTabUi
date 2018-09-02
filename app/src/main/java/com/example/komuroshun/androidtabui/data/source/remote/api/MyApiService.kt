package com.example.komuroshun.androidtabui.data.source.remote.api

import com.example.komuroshun.androidtabui.data.source.remote.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiService {
    
    @GET("forecast/webservice/json/v1")
    fun weatherInfo(@Query("city") cityId: Int): Call<WeatherInfo>
}
