package com.harshithakv.weatherandnewsinfo.models
import com.google.gson.annotations.SerializedName


data class TopHeadlines(
    @SerializedName("articles")
    var articlesTop: List<ArticleTop>,
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResults: Int
)

data class ArticleTop(
    @SerializedName("author")
    var author: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("publishedAt")
    var publishedAt: String,
    @SerializedName("source")
    var source: Source,
    @SerializedName("title")
    var title: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("urlToImage")
    var urlToImage: String
)

data class Source(
    @SerializedName("id")
    var id: Any,
    @SerializedName("name")
    var name: String
)