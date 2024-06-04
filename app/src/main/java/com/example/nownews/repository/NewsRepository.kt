package com.example.nownews.repository

import com.example.nownews.model.NewsResponse
import com.example.nownews.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(private val api: NewsApi) {

    suspend fun getHeadlines(apiKey: String, country: String, pageSize: Int): NewsResponse {
        return withContext(Dispatchers.IO) {
            api.getHeadlines(apiKey, country, pageSize)
        }
    }

    suspend fun getEverything(apiKey: String, q: String, pageSize: Int): NewsResponse {
        return withContext(Dispatchers.IO) {
            api.getEverything(apiKey, q, pageSize)
        }
    }
}