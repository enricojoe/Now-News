package com.example.nownews.network

import com.example.nownews.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getEverything(@Query("apiKey") apiKey: String,
                              @Query("q") query: String,
                              @Query("pageSize") pageSize: Int?) : NewsResponse

    @GET("top-headlines")
    suspend fun getHeadlines(@Query("apiKey") apiKey: String,
                             @Query("country") country: String,
                             @Query("pageSize") pageSize: Int?) : NewsResponse
}