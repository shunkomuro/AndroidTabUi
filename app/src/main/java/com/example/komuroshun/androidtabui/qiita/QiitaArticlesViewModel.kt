package com.example.komuroshun.androidtabui.qiita

import android.content.Context
import android.databinding.BaseObservable
import android.net.Uri
import android.view.View
import com.example.komuroshun.androidtabui.data.repository.QitaArticlesRepository
import com.example.komuroshun.androidtabui.data.source.remote.QitaArticle
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.URLEncoder

// TODO: Context は Dagger2 で Inject する
class QiitaArticlesViewModel(context: Context, var fragment: QiitaArticlesFragment): BaseObservable() {

    var mContext: Context;
    var mQitaArticlesRepository: QitaArticlesRepository = QitaArticlesRepository.create()

    companion object {
        fun create(context: Context, fragment: QiitaArticlesFragment): QiitaArticlesViewModel = QiitaArticlesViewModel(context, fragment)
    }

    init {
        mContext = context
    }

    fun getQitaArticleListAdapter() {

        mQitaArticlesRepository.getQitaArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<List<QitaArticle>> {
            override fun onSubscribe(d: Disposable?) {}
            override fun onNext(articles: List<QitaArticle>) {
                var adapter = QiitaArticlesAdapter(articles)
                adapter.setOnItemClickListener(object: QiitaArticlesAdapter.OnItemClickListener{
                    override fun onClick(view: View?, data: QitaArticle) {
                        var uri: Uri =Uri.parse("sampleapp://" +
                                "androidtabui.example.com" +
                                "?url=${URLEncoder.encode(data.url, "UTF-8")}" +
                                "#com.example.komuroshun.androidtabui.WebViewFragment")
                        fragment.createFragmentOnCurrentPage(uri)
                    }
                })
                fragment.setAdapter(adapter)
            }
            override fun onError(e: Throwable?) {}
            override fun onComplete() {}
        })
    }
}