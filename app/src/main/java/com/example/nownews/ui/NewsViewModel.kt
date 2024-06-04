package com.example.nownews.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.nownews.BuildConfig
import com.example.nownews.model.NewsResponse
import com.example.nownews.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _headlinesLiveData = MutableLiveData<NewsResponse>()
    private val _newsLiveData = MutableLiveData<NewsResponse>()
    private val API_KEY = BuildConfig.API_KEY
    val combinedNewsLiveData: MediatorLiveData<Pair<NewsResponse?, NewsResponse?>> = MediatorLiveData()

    init {
        fetchHeadlines(API_KEY, "us", 20)
        fetchNews(API_KEY, "finance", 30)

        combinedNewsLiveData.addSource(_headlinesLiveData) { topHeadlinesResult ->
            combinedNewsLiveData.value = Pair(topHeadlinesResult, _newsLiveData.value)
        }

        combinedNewsLiveData.addSource(_newsLiveData) { allNewsResult ->
            combinedNewsLiveData.value = Pair(_headlinesLiveData.value, allNewsResult)
        }
    }

    private fun fetchHeadlines(apiKey: String, country: String, pageSize: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getHeadlines(apiKey, country, pageSize)
                Log.d("NewsRepository", "getHeadline response: $response")
                _headlinesLiveData.value = response
            } catch (e: Exception) {
                Log.d("HeadlineError", e.toString())
            }
        }
    }

    private fun fetchNews(apiKey: String, q: String, pageSize: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getEverything(apiKey, q, pageSize)
                Log.d("NewsRepository", "getNews response: $response")
                _newsLiveData.value = response
            } catch (e: Exception) {
                Log.d("NewsError", e.toString())
            }
        }
    }
}