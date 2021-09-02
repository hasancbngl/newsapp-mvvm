package com.cobanogluhasan.news_app.data.repository

import com.cobanogluhasan.news_app.data.api.NewsApi
import com.cobanogluhasan.news_app.data.api.RetrofitInstance
import com.cobanogluhasan.news_app.data.db.ArticleDatabase

class NewsRepository(val db: ArticleDatabase, val api: RetrofitInstance) {

}