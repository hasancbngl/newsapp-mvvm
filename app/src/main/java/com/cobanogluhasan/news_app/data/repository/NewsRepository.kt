package com.cobanogluhasan.news_app.data.repository

import com.cobanogluhasan.news_app.data.api.RetrofitInstance
import com.cobanogluhasan.news_app.data.db.ArticleDatabase

class NewsRepository(val db : ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}