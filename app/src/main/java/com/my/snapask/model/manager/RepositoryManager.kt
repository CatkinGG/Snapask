package com.my.snapask.model.manager

import com.google.gson.Gson
import com.my.snapask.model.api.ApiService
import com.my.snapask.model.repository.HomeRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RepositoryManager(
    private val restfulOkHttpClient: OkHttpClient
) {
    val domain = "https://api.github.com/"

    val homeRepository by lazy { HomeRepository(apiService) }

    private val apiService by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        restfulOkHttpClient.interceptors.toMutableList().add(interceptor)

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(restfulOkHttpClient)
            .baseUrl(domain)
            .build()
            .create(ApiService::class.java)
    }
}