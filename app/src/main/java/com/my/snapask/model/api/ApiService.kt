package com.my.snapask.model.api

import com.my.snapask.model.api.vo.RepoItem
import com.my.snapask.model.api.vo.UserItem
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("/users")
    suspend fun getUsers(
    ): Response<List<UserItem>>

    @GET("/users/{username}")
    suspend fun getUserByUserName(
        @Path("username") username: String
    ): Response<UserItem>

    @GET("/users/{username}/repos")
    suspend fun getUserRepos(
        @Path("username") username: String
    ): Response<List<RepoItem>>
}