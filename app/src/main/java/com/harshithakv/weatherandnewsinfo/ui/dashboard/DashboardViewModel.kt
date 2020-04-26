package com.harshithakv.weatherandnewsinfo.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harshithakv.openweathernews.models.EverythingNews
import com.harshithakv.openweathernews.repository.CurrentWeatherRepo
import com.harshithakv.openweathernews.requests.news.NewsApiClient.getAllNews
import com.harshithakv.weatherandnewsinfo.models.TopHeadlines

class DashboardViewModel(var currentWeatherRepo: CurrentWeatherRepo) : ViewModel() {
    fun getAllNews() {
        currentWeatherRepo.getAllNews()
    }



    fun allNewsLiveData() : LiveData<TopHeadlines?>? {
        return currentWeatherRepo.allNewsLiveData()
    }

    fun pushUrl(url:String){
        currentWeatherRepo.pushUrl(url)
    }
}