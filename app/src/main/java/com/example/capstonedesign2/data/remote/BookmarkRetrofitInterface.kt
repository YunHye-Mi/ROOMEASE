package com.example.capstonedesign2.data.remote

import com.example.capstonedesign2.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookmarkRetrofitInterface {
    @POST("/api/bookmark/add")
    fun addBookmark(@Header(value = "Authorization") authorization: String, @Query(value = "room_id") room_id: Int): Call<BookmarkResponse>

    @GET("/api/bookmark/list")
    fun getBookmark(@Header(value = "Authorization") authorization: String): Call<BookmarkResponse>

    @GET("/api/broker/room/list")
    fun getBrokerEstates(@Header(value = "Authorization") authorization: String): Call<BookmarkResponse>
}