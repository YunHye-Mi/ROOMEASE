package com.example.capstonedesign2.data.remote

import retrofit2.Call
import retrofit2.http.*

interface ChatRetrofitInterface {

    @POST("/api/chatroom/create")
    fun createChatRoom(@Header(value = "Authorization") authorization: String, @Body brokerId: BrokerIdRequest): Call<CreateRoomResponse>

    @GET("/api/chatroom/list")
    fun getChatRoom(@Header(value = "Authorization") authorization: String): Call<ChatRoomResultResponse>

    @GET("/api/chatroom/chat-list")
    fun getBeforeChat(@Header(value = "Authorization") authorization: String, @Query("chatRoomId") chatRoomId: Int): Call<BeforeChatResponse>
}