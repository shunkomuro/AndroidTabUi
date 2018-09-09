package com.example.komuroshun.androidtabui.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.android.databinding.library.baseAdapters.BR
import com.example.komuroshun.androidtabui.data.repository.WeatherInfoRepository
import com.example.komuroshun.androidtabui.data.source.remote.WeatherInfo
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherInfoViewModel: BaseObservable() {
    //TODO It may change the place to implement.Want to inject
    val weatherInfoRepository: WeatherInfoRepository = WeatherInfoRepository.create()
    private var mCityName: String = ""
    private var mTelop: String = ""
    private var mDescription: String = ""

    @Bindable
    fun getMCityName(): String {
        return mCityName
    }
    fun setMCityName(cityName: String) {
        mCityName = cityName
        notifyPropertyChanged(BR.mCityName)
    }
    @Bindable
    fun getMTelop(): String {
        return mTelop
    }
    fun setMTelop(telop: String) {
        mTelop = telop
        notifyPropertyChanged(BR.mTelop)
    }
    @Bindable
    fun getMDescription(): String {
        return mDescription
    }
    fun setMDescription(description: String) {
        mDescription = description
        notifyPropertyChanged(BR.mDescription)
    }

    companion object {
        fun create(): WeatherInfoViewModel = WeatherInfoViewModel()
    }

    fun getWeatherInfo(cityId: Int, cityName: String) {
        Log.d("MYTEST", "Call getWeaherInfo on WeatherInfoViewModel !!")
        //        val myApiService: MyApiService =  Api.newInstance().myApiService!!
//        val weatherInfoObservable: Observable<WeatherInfo> = myApiService!!.weatherInfo(cityId)
//        val weatherInfoObservable2: Observable<WeatherInfo> = myApiService!!.weatherInfo(cityId)
//        val Observable1: Observable<WeatherInfo> = Observable.merge(weatherInfoObservable, weatherInfoObservable2)
//        val Observable2: Observable<WeatherInfo> = Observable.merge(Observable1, weatherInfoObservable2)
        weatherInfoRepository.getWeatherInfoByCityId(cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<WeatherInfo> {
                    override fun onSubscribe(d: Disposable?) {}
                    override fun onNext(weatherInfo: WeatherInfo?) {
                        Log.d("MYTEST", "weatherInfo from WeatherInfoViewModel next !")
                        if (weatherInfo!!.forecasts.isNotEmpty()) {
                            var cityName = cityName + "の" + weatherInfo!!.forecasts[0].dateLabel + "の天気: "
                            setMCityName(cityName)
                            var telop = weatherInfo!!.forecasts[0].telop
                            setMTelop(telop)
                        }
                        var description = weatherInfo!!.description.text
                        setMDescription(description)
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("MYTEST", "weatherInfo from WeatherInfoViewModel error !")
                    }

                    override fun onComplete() {
                        Log.d("MYTEST", "weatherInfo from WeatherInfoViewModel complete !")
                    }
                })
    }
}