package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName

data class RoomResponse(
    @SerializedName("status") val  status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val roomResult: RoomResult
)

data class MapResponse(
    @SerializedName("id") val id: String,
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String
)

data class MapRequest(
    @SerializedName("centerlat") val centerlat: String,
    @SerializedName("centerlng") val centerlng: String,
    @SerializedName("zoomlevel") val zoomlevel: Int
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
