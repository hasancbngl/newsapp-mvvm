package com.cobanogluhasan.news_app.data.repository

import com.cobanogluhasan.model.Article
import com.cobanogluhasan.news_app.data.api.RetrofitInstance
import com.cobanogluhasan.news_app.data.db.ArticleDatabase

class NewsRepository(val db: ArticleDatabase) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchArticles(searchQuery, pageNumber)

    suspend fun insertArticle(article: Article) = db.getArticleDao().insertArticle(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}