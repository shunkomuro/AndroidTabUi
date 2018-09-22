package com.example.komuroshun.androidtabui.card

import android.content.Context
import android.content.pm.PackageInfo
import android.databinding.BaseObservable
import com.example.komuroshun.androidtabui.data.repository.AppPackageRepository

class CardListViewModel(context: Context): BaseObservable() {
    private var mContext: Context? = null
    private val mAppPackageRepository: AppPackageRepository = AppPackageRepository.create()

    companion object {
        fun create(context: Context): CardListViewModel = CardListViewModel(context)
    }

    init {
        mContext = context
        //TODO: Dagger2 を使うようにする
        mAppPackageRepository.setContext(context)
    }

    //TODO: Adapter の扱いはこれでいいのか
    fun getCardListAdapter(): CardListAdapter {
        val cardItemViewModelList: ArrayList<CardItemViewModel> = ArrayList()
        mAppPackageRepository.getAppPackages()
                .map(object: (PackageInfo)->Unit {
                    override fun invoke(packageInfo: PackageInfo) {
                        var cardItemViewModel: CardItemViewModel = CardItemViewModel.create()
                        cardItemViewModel.setAppTitle(packageInfo.applicationInfo
                                .loadLabel(mContext!!.packageManager) as String)
                        cardItemViewModel.setPackageDetail(packageInfo.packageName,
                                packageInfo.versionName, Integer.toString(packageInfo.versionCode))
                        cardItemViewModel.setPackageIcon(packageInfo.applicationInfo
                                .loadIcon(mContext!!.packageManager))
                        cardItemViewModelList.add(cardItemViewModel)
                    }

                }).subscribe()

        val adapter = CardListAdapter(mContext!!)
        // アダプターにアプリ情報を追加
        if (cardItemViewModelList != null) {
            for (info in cardItemViewModelList) {
                adapter.add(info)
            }
        }

        return adapter
    }
}