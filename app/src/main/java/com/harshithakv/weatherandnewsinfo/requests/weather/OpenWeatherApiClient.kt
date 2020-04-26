package com.harshithakv.openweathernews.requests.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harshithakv.openweathernews.models.CurrentWeather
import com.harshithakv.openweathernews.models.WeatherForecast
import com.harshithakv.openweathernews.requests.ServiceGenerator
import com.harshithakv.openweathernews.requests.news.NewsApiClient
import kotlinx.coroutines.*

object OpenWeatherApiClient {
    private var mWeather: MutableLiveData<CurrentWeather?> = MutableLiveData()
    private var forecastWeather: MutableLiveData<WeatherForecast?> = MutableLiveData()
    private const val key = "71ed5868f11ea02543d4602f2fc77ccf"
    private const val TIME_OUT = 3000L

    fun getCurrWeather(city: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            var job = withTimeoutOrNull(TIME_OUT) {
                val response = ServiceGenerator.getWeatherService().getCurrentWeather(city, "metric", key)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful || response.code() == 200) {
                        val currentWeather = response.body()
                        mWeather.postValue(currentWeather)
                    } else {
                        Log.i("demo", "$response.message()")
                    }
                }
            }
            if (job == null) {
                Log.i("demo", "TOOK TOO MUCH TIME")
            }
        }
    }


    fun getForecastWeather(city: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            var job = withTimeoutOrNull(TIME_OUT) {
                val response =
                    ServiceGenerator.getWeatherService().getWeatherForecast(city, "metric", key)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful || response.code() == 200) {
                        val weatherForecast = response.body()
                        forecastWeather.postValue(weatherForecast)
                    } else {
                        Log.i("demo", "$response.message()")
                    }

                }

            }
            if (job == null) {
                Log.i("demo", "TOOK TOO MUCH TIME")
            }
        }
    }


    fun getWeatherLiveData(): LiveData<CurrentWeather?>? {
        return mWeather
    }


    fun getForecastLiveData(): LiveData<WeatherForecast?>? {
        return forecastWeather
    }


}