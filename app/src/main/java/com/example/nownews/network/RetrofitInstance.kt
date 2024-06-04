package com.example.nownews.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object{
        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        private val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .writeTimeout(25,TimeUnit.SECONDS)
        }.build()

        val api : NewsApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsApi::class.java)
        }
    }
}