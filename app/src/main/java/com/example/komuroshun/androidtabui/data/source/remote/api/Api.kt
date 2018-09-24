package com.example.komuroshun.androidtabui.data.source.remote.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api {

    var weatherLivedoorApiService: WeatherLivedoorApiService? = null
    val qitaApiService: QitaApiService

    companion object {
        fun newInstance(): Api = Api()
    }

    init {
        // TODO 呼ばれた時に都度初期化するようにする
        weatherLivedoorApiService =
                createWeatherLivedoorApiService(WeatherLivedoorApiService::class.java)
        qitaApiService = createQitaApiService(QitaApiService::class.java)
    }

    private fun <S> createWeatherLivedoorApiService(serviceClass: Class<S>): S {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val gson = GsonBuilder()
                .serializeNulls()
                .create()

        val retrofitBuilder = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://weather.livedoor.com/")
                .build()

        return retrofitBuilder.create(serviceClass)
    }

    private fun <S> createQitaApiService(serviceClass: Class<S>): S {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val gson = GsonBuilder()
                .serializeNulls()
                .create()

        val retrofitBuilder = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://qiita.com/")
                .build()

        return retrofitBuilder.create(serviceClass)
    }
}