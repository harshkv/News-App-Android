package com.harshithakv.openweathernews.requests.news

import com.harshithakv.openweathernews.models.EverythingNews
import com.harshithakv.weatherandnewsinfo.models.TopHeadlines
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getCityNews(
        @Query("q") q: String?,
        @Query("apiKey") apiKey: String?
    ): Response<EverythingNews?>


    @GET("top-headlines")
    suspend fun getAllNews(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String?
    ): Response<TopHeadlines?>
}

