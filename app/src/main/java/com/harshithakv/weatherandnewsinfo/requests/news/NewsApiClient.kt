package com.harshithakv.openweathernews.requests.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harshithakv.openweathernews.models.EverythingNews
import com.harshithakv.openweathernews.requests.ServiceGenerator
import com.harshithakv.weatherandnewsinfo.models.TopHeadlines
import kotlinx.coroutines.*

object NewsApiClient {
    var newsLiveData: MutableLiveData<EverythingNews?> = MutableLiveData()
    var allNewsLiveData: MutableLiveData<TopHeadlines?> = MutableLiveData()
    private const val key = "39f746c057f2488eb3876c9be74d9837"
    private lateinit var urls: String
    private const val TIME_OUT = 3000L

    fun getCurrentNews(q: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            var job = withTimeoutOrNull(TIME_OUT) {
                val response = ServiceGenerator.getNewsService()
                    .getCityNews(
                        q,
                        key
                    )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful || response.code() == 200) {
                        val topNews = response.body()
                        newsLiveData.postValue(topNews)
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


    fun getAllNews() {
        CoroutineScope(Dispatchers.IO).launch {
            var job = withTimeoutOrNull(TIME_OUT) {
                val response = ServiceGenerator.getNewsService()
                    .getAllNews(
                        "us",
                        key
                    )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful || response.code() == 200) {
                        val topHeadlines = response.body()
                        allNewsLiveData.postValue(topHeadlines)

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

    fun getTopNewsLiveData(): LiveData<EverythingNews?>? {
        return newsLiveData
    }

   fun allNewsLiveData() : LiveData<TopHeadlines?>? {
       return allNewsLiveData
   }
}