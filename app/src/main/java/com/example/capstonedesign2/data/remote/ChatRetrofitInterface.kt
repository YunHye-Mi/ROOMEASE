package com.example.capstonedesign2.data.remote

import retrofit2.Call
import retrofit2.http.*

interface ChatRetrofitInterface {

    @POST("/api/chatroom/create")
    fun createChatRoom(@Header(value = "Authorization") authorization: String, @Body brokerId: Int): Call<ChatResponse>

    @GET("/api/chatroom/list")
    fun getChatRoom(@Header(value = "Authorization") authorization: String): Call<ChatResponse>
}