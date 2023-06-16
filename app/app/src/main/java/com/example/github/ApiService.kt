package com.example.github

import retrofit2.http.*

interface ApiService {
    //tampilin list rumah sakit
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUserCapstone(
    ): MutableList<ItemsItem>

    //tampilin detail rumah sakitnya
    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailCapstone(
        @Path("username") username: String
    ): ResponseDetailGithub

    //login
    @POST("login")
    suspend fun postLogin(
        @Path("email") email: String
    ): ResponseLogin

}