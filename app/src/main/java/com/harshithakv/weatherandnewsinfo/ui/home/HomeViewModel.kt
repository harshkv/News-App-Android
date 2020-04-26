package com.harshithakv.weatherandnewsinfo.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harshithakv.openweathernews.models.CurrentWeather
import com.harshithakv.openweathernews.models.EverythingNews
import com.harshithakv.openweathernews.models.Listt
import com.harshithakv.openweathernews.models.WeatherForecast
import com.harshithakv.openweathernews.repository.CurrentWeatherRepo

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(var currentWeatherRepo: CurrentWeatherRepo) : ViewModel() {
    fun getWeatherInfo(): LiveData<CurrentWeather?>? {
        return currentWeatherRepo.getWeatherInfo()
    }

    fun getCurrWeather(city: String?) {
        currentWeatherRepo.getCurrWeather(city)
    }


    fun getForecastInfo(): LiveData<WeatherForecast?>? {
        return currentWeatherRepo.getForecaseInfo()
    }

    fun getTopNewsInfo(): LiveData<EverythingNews?>? {
        return currentWeatherRepo.getTopNewsInfo()
    }


    fun getForecastWeather(city: String?) {
        currentWeatherRepo.getForecastWeather(city)
    }

    fun getCityNews(query: String?) {
        currentWeatherRepo.getCityNews(query)
    }

    fun calculateMax(
        tempList: List<Listt>,
        maps: HashMap<String?, Int?>,
        maxIcon: HashMap<String?, String?>
    ): HashMap<String?, Int?> {
        for (i in tempList.indices) {
            var max = tempList[i].main.tempMax.toInt()
            var icon: String = tempList[i].weather.get(0).icon
            val outputText: String = dateFormater(tempList[i].dtTxt) as String
            if (maps.containsKey(outputText)) {
                if (max > maps[outputText]!!) {
                    maps[outputText] = max
                    maxIcon[outputText] = icon
                }
            } else {
                maps[outputText] = max
                maxIcon[outputText] = icon
            }
        }
        return maps
    }


    fun calculateMin(
        tempList: List<Listt>,
        maps: HashMap<String?, Int?>
    ): HashMap<String?, Int?> {
        for (i in tempList.indices) {
            var min = tempList[i].main.tempMin.toInt()
            var outputText: String = dateFormater(tempList[i].dtTxt) as String
            if (maps.containsKey(outputText)) {
                if (min < maps[outputText]!!) {
                    maps[outputText] = min
                }
            } else {
                maps[outputText] = min
            }
        }
        return maps
    }

    private fun dateFormater(inputText: String): String? {
        val outputFormat: DateFormat =
            SimpleDateFormat("MM-dd-yyyy", Locale.US)
        val inputFormat: DateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        var date: Date? = null
        try {
            date = inputFormat.parse(inputText)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputFormat.format(date)
    }


    fun imageLoader(icon: String): String? {
        return "http://openweathermap.org/img/wn/$icon@2x.png"
    }

    fun nextdays(forecastDays: MutableList<String?>) {
        val date = System.currentTimeMillis()
        val sdf = SimpleDateFormat("MM-dd-yyyy")
        val dateString = sdf.format(date)
        val c = Calendar.getInstance()
        try {
            c.time = sdf.parse(dateString)
            for (i in 1..3) {
                c.add(Calendar.DATE, 1)
                forecastDays.add(sdf.format(c.time))
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }


    fun pushUrl(url:String){
        currentWeatherRepo.pushUrl(url)
    }
}