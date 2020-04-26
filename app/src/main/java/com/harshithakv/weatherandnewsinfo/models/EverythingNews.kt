package com.harshithakv.openweathernews.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class EverythingNews(
    @SerializedName("articles")
    var articles: List<Article>,
    @SerializedName("status")
    var status: String,
    @SerializedName("totalResults")
    var totalResults: Int
): Serializable

data class Article(
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
): Serializable

data class Source(
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String
)