package com.example.capstonedesign2.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/api/auth/login")
    fun login(@Body authRequest: AuthRequest): Call<AuthResponse>

    @POST
    fun refresh(@Header("Authorization") authorization: String, @Body refreshRequest: RefreshRequest): Call<AuthResponse>

    @POST("/api/auth/registor")
    fun register(@Header(value = "Authorization") authorization: String, @Body registerRequest: RegisterRequest): Call<RegisterResponse>

}