package com.example.komuroshun.androidtabui.data.source.remote.api

import com.example.komuroshun.androidtabui.data.source.remote.QitaArticle
import com.example.komuroshun.androidtabui.data.source.remote.WeatherInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface QitaApiService {

    @GET("api/v2/items")
    fun getArticles(): Observable<List<QitaArticle>>
}
