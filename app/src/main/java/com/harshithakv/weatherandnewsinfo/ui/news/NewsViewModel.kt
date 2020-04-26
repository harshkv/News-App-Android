package com.harshithakv.weatherandnewsinfo.ui.news

import android.util.Log
import androidx.lifecycle.ViewModel
import com.harshithakv.openweathernews.repository.CurrentWeatherRepo

class NewsViewModel(var currentWeatherRepo: CurrentWeatherRepo): ViewModel() {
    fun getUrl(): String {
       return currentWeatherRepo.getUrl().toString()
    }
}