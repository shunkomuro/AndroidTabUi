package com.example.komuroshun.androidtabui.data.repository

import com.example.komuroshun.androidtabui.data.source.remote.QitaArticle
import com.example.komuroshun.androidtabui.data.source.remote.api.Api
import com.example.komuroshun.androidtabui.data.source.remote.api.QitaApiService

import io.reactivex.Observable

class QitaArticlesRepository {

    private var mQitaApiService: QitaApiService

    companion object {
        fun create(): QitaArticlesRepository = QitaArticlesRepository()
    }

    init {
        mQitaApiService = Api.newInstance().qitaApiService
    }

    fun getQitaArticles(): Observable<List<QitaArticle>> {
        return mQitaApiService.getArticles()
    }
}