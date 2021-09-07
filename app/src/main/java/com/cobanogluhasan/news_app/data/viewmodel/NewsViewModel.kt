package com.cobanogluhasan.news_app.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cobanogluhasan.model.Article
import com.cobanogluhasan.model.NewsResponse
import com.cobanogluhasan.news_app.data.repository.NewsRepository
import com.cobanogluhasan.news_app.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    val breakingNewsData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null
    val searchNewsData: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPageNumber = 1
    var searchNewsResponse: NewsResponse? = null

    fun getBreakingNews(countryCode: String) {
        viewModelScope.launch {
            breakingNewsData.postValue(Resource.Loading())
            val response = repository.getBreakingNews(countryCode, breakingNewsPage)
            breakingNewsData.postValue(handleBreakingNewsResponse(response))
        }
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNewsData.postValue(Resource.Loading())
        val response = repository.searchNews(searchQuery, searchNewsPageNumber)
        searchNewsData.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) breakingNewsResponse = resultResponse
                else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPageNumber++
                if (searchNewsResponse == null) searchNewsResponse = resultResponse
                else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.insertArticle(article)
    }

    fun getSavedNews() = repository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }
}