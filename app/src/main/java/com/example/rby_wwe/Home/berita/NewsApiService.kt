package com.example.rby_wwe.Home.berita

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("api.json")
    fun getNews(
        // Menggunakan RSS CNN Indonesia yang jauh lebih stabil
        @Query("rss_url") rssUrl: String = "https://www.cnnindonesia.com/nasional/rss"
    ): Call<NewsResponse>
}
