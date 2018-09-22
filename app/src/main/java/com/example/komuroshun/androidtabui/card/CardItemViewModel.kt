package com.example.komuroshun.androidtabui.card

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.graphics.drawable.Drawable
import com.example.komuroshun.androidtabui.BR

class CardItemViewModel: BaseObservable() {
    private var appTitle: String? = null
    private var packageDetail: String? = null
    private var packageIcon: Drawable? = null

    companion object {
        fun create(): CardItemViewModel = CardItemViewModel()
    }

    @Bindable
    fun getAppTitle(): String? {
        return appTitle
    }

    fun setAppTitle(s: String) {
        appTitle = s
        notifyPropertyChanged(BR.appTitle)
    }

    @Bindable
    fun getPackageDetail(): String? {
        return packageDetail
    }

    fun setPackageDetail(packageName: String, versionName: String, versionCode: String) {
        packageDetail = (packageName + System.getProperty("line.separator")
            + "versionName : " + versionName + System.getProperty("line.separator")
            + "versionCode : " + versionCode)
        notifyPropertyChanged(BR.packageDetail)
    }

    @Bindable
    fun getPackageIcon(): Drawable? {
        return packageIcon
    }

    fun setPackageIcon(drawable: Drawable) {
        packageIcon = drawable
        notifyPropertyChanged(BR.packageIcon)
    }
}