package com.example.capstonedesign2.data.remote

import retrofit2.Call
import retrofit2.http.*

interface ReminderRetrofitInterface {

    @POST("/api/chatroom/reminder")
    fun addReminder(@Header(value = "Authorization") authorization: String, @Body reminder: Reminder): Call<ReminderResponse>

    @GET("/api/chatroom/reminder")
    fun getReminder(@Header(value = "Authorization") authorization: String, @Query(value = "chatRoomId") roomId: Long): Call<ReminderResponse>
}