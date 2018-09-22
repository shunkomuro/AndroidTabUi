package com.example.komuroshun.androidtabui.data.repository

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import io.reactivex.Observable

class AppPackageRepository {
    //TODO: Delete the following variable after implementing Dagger
    private var mContext: Context? = null
    //TODO: Delete the following method after implementing Dagger
    fun setContext(context: Context) {
        mContext = context
    }

    companion object {
        fun create(): AppPackageRepository = AppPackageRepository()
    }

    //TODO Use Single RxJava class ?
    fun getAppPackages(): Observable<PackageInfo> {
        // Get installed apps.
        val packageInfoList = mContext!!.packageManager
                .getInstalledPackages(PackageManager.GET_ACTIVITIES)
        return Observable.fromIterable(packageInfoList)
    }
}