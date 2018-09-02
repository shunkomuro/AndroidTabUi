package com.example.komuroshun.androidtabui.data.source.remote.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api() {
    var myApiService: MyApiService? = null

    companion object {
        fun newInstance(): Api = Api()
    }
    init {
        myApiService = createApiService(MyApiService::class.java)
    }

    private fun <S> createApiService(serviceClass: Class<S>): S {
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
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("http://weather.livedoor.com/") // Put your base URL
                .build()

        return retrofitBuilder.create(serviceClass)
    }
}