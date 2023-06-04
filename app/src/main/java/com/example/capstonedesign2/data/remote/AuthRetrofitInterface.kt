package com.example.capstonedesign2.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/api/auth/login")
    fun login(@Body authRequest: AuthRequest): Call<AuthResponse>

    @POST("/api/auth/refresh")
    fun refresh(@Header(value = "Authorization") authorization: String, @Body refreshToken: RefreshRequest): Call<AuthResponse>

    @POST("/api/auth/register")
    fun register(@Header(value = "Authorization") authorization: String, @Body registerRequest: RegisterRequest): Call<RegisterResponse>

}