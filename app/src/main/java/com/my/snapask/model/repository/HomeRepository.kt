package com.my.snapask.model.repository

import com.my.snapask.model.api.ApiService


class HomeRepository constructor(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers()

    suspend fun getUserByUserName(username: String) = apiService.getUserByUserName(username)

    suspend fun getUserRepos(username: String) = apiService.getUserRepos(username)
}