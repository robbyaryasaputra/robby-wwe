package com.example.rby_wwe.Home.berita

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status: String?,
    @SerializedName("items") val posts: List<NewsPost>?
)

data class NewsPost(
    @SerializedName("title") val title: String?,
    @SerializedName("pubDate") val pubDate: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("link") val link: String?,
    @SerializedName("enclosure") val enclosure: NewsEnclosure?
)

data class NewsEnclosure(
    @SerializedName("link") val link: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("thumbnail") val thumbnail: String?
)
