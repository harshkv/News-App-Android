package com.harshithakv.openweathernews.requests.weather

import com.harshithakv.openweathernews.models.CurrentWeather
import com.harshithakv.openweathernews.models.WeatherForecast
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") q: String?,
        @Query("units") units: String?,
        @Query("appid") appid: String?
    ): Response<CurrentWeather?>


    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("q") q: String?,
        @Query("units") units: String?,
        @Query("appid") appid: String?
    ): Response<WeatherForecast?>

}