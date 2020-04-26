package com.harshithakv.openweathernews.repository



import androidx.lifecycle.LiveData
import com.harshithakv.openweathernews.models.CurrentWeather
import com.harshithakv.openweathernews.models.EverythingNews
import com.harshithakv.openweathernews.models.WeatherForecast
import com.harshithakv.openweathernews.requests.news.NewsApiClient
import com.harshithakv.openweathernews.requests.weather.OpenWeatherApiClient
import com.harshithakv.weatherandnewsinfo.models.TopHeadlines


object CurrentWeatherRepo {
    var openWeatherApiClient =
        OpenWeatherApiClient
    var newsApiClient =
        NewsApiClient

    fun getWeatherInfo(): LiveData<CurrentWeather?>? {
        return openWeatherApiClient.getWeatherLiveData()
    }


    fun getCurrWeather(city: String?) {
        openWeatherApiClient.getCurrWeather(city)
    }


    fun getForecaseInfo(): LiveData<WeatherForecast?>? {
        return openWeatherApiClient.getForecastLiveData()
    }

    fun getTopNewsInfo(): LiveData<EverythingNews?>? {
        return newsApiClient.getTopNewsLiveData()
    }

    fun getForecastWeather(city: String?) {
        openWeatherApiClient.getForecastWeather(city)
    }

    fun getCityNews(q: String?) {
        newsApiClient.getCurrentNews(q)
    }

    lateinit var urls:String
    fun pushUrl(url:String){
        urls = url

    }

    fun getUrl():String? {
        return urls
    }


    fun getAllNews() {
         newsApiClient.getAllNews()
    }

    fun allNewsLiveData() : LiveData<TopHeadlines?>? {
        return newsApiClient.allNewsLiveData()
    }

}