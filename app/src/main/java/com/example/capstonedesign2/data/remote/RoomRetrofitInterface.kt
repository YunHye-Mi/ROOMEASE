package com.example.capstonedesign2.data.remote

import com.example.capstonedesign2.data.entities.Estate
import retrofit2.Call
import retrofit2.http.*

interface RoomRetrofitInterface {

    @POST("/api/broker/estate/:room_id")
    fun addRoom(@Header(value = "Authorization") authorization: String,): Call<RoomResponse>

    @Headers("Content-Type: application/json")
    @GET("/api/room")
    fun getZoomOUt(
        @Query("centerlat") centerlat: Double,
        @Query("centerlng") centerlng: Double,
        @Query("minRent") minRent: Double?,
        @Query("maxRent") maxRent: Double?,
        @Query("type") type: Int?,
        @Query("minSize") minSize: Double?,
        @Query("maxSize") maxSize: Double?,
        @Query("minDeposit") minDeposit: Int?,
        @Query("maxDeposit") maxDeposit: Int?,
        @Query("minManage") minManage: Double?,
        @Query("maxManage") maxManage: Double?,
        @Query("lbLat") lbLat: Double, // 좌측 하단 위도
        @Query("lbLng") lbLng: Double, // 좌측 하단 경도
        @Query("rtLat") rtLat: Double, // 우측 상단 위도
        @Query("rtLng") rtLng: Double // 우측 상단 경도
    ): Call<MapZoomOutResponse>

    @Headers("Content-Type: application/json")
    @GET("/api/room")
    fun getZoomIn(
        @Query("centerlat") centerlat: Double,
        @Query("centerlng") centerlng: Double,
        @Query("minRent") minRent: Double?,
        @Query("maxRent") maxRent: Double?,
        @Query("type") type: Int?, // 전월세 0과 1로 구분
        @Query("minSize") minSize: Double?,
        @Query("maxSize") maxSize: Double?,
        @Query("minDeposit") minDeposit: Int?,
        @Query("maxDeposit") maxDeposit: Int?,
        @Query("minManage") minManage: Double?,
        @Query("maxManage") maxManage: Double?,
        @Query("lbLat") lbLat: Double, // 좌측 하단 위도
        @Query("lbLng") lbLng: Double, // 좌측 하단 경도
        @Query("rtLat") rtLat: Double, // 우측 상단 위도
        @Query("rtLng") rtLng: Double, // 우측 상단 경도
        @Query("minFloor") minFloor: Int?,
        @Query("maxFloor") maxFloor: Int?
    ): Call<MapZoomInResponse>

    @GET("/api/detail")
    fun getRoomDetail(@Query("room_id") id: String): Call<Estate>
}