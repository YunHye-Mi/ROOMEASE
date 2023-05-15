package com.example.capstonedesign2.data.remote

import com.example.capstonedesign2.data.entities.Estate
import retrofit2.Call
import retrofit2.http.*

interface RoomRetrofitInterface {

    @POST("/api/broker/estate/:room_id")
    fun addRoom(@Header(value = "Authorization") authorization: String,): Call<RoomResponse>

    @Headers("Content-Type : application/json")
    @POST("/api/estate/search")
    fun searchRooms(): Call<RoomResponse>

    @Headers("Content-Type : application/json")
    @POST("/api/estate/")
    fun getRooms(@Body mapResponse: MapResponse): Call<MapResponse>

    @GET("/api/estate/{room_id}")
    fun getRoomDetail(@Path(value = "room_id") id: String ,@Body mapResponse: MapResponse): Call<Estate>
}