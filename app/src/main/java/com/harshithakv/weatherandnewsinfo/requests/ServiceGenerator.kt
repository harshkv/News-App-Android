package com.harshithakv.openweathernews.requests


import com.harshithakv.openweathernews.requests.news.NewsApi
import com.harshithakv.openweathernews.requests.weather.OpenWeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    private const val weatherBaseUrl: String = "https://api.openweathermap.org/data/2.5/"
    private const val newsBaseUrl: String = "https://newsapi.org/v2/"

    fun getWeatherService(): OpenWeatherApi {
        return Retrofit.Builder()
            .baseUrl(weatherBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }


    fun getNewsService(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(newsBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

}
