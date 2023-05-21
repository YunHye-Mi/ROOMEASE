package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class RoomResponse(
    @SerializedName("status") val  status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val objects: Objects?
)

data class MapZoomOutResponse(
    @SerializedName("ids") val ids: Array<String>,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("minprice") val minPrice: Double,
    @SerializedName("dongname") val dongName: String,
    @SerializedName("roomNum") val roomNum: Int
)

data class MapZoomInResponse(
    @SerializedName("id") val id: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("price") val price: Double,
    @SerializedName("size") val size: Double
)

data class Address(
    @SerializedName(value = "address") var address: String,
    @SerializedName(value = "count") var count: Int
)

data class RoomResult(
    @SerializedName(value = "estateIdx") val estateIdx: Int,
    @SerializedName(value = "estateName") val estateName: String,
    @SerializedName(value = "estateType") val estateType: String
)
