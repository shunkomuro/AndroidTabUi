package com.example.komuroshun.androidtabui.data.source.remote

data class WeatherInfo (var description: WeatherDescription, var forecasts: List<WeatherForecast>)

data class  WeatherDescription (var text: String)

data class WeatherForecast (var dateLabel: String,
                             var telop: String)