package com.cobanogluhasan.news_app.data.api

import com.cobanogluhasan.news_app.NewsResponse
import com.cobanogluhasan.news_app.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    //make it suspend func to use it in coroutines
    //get country news, limited articles at once with pages
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    //searchForAllArticles
    @GET("v2/everything")
    suspend fun searchArticles(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}