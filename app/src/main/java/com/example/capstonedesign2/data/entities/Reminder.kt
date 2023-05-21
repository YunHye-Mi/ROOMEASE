package com.example.capstonedesign2.data.entities

import com.google.gson.annotations.SerializedName

data class Reminder(
    @SerializedName("roomId") var roomId: Int,
    @SerializedName("date") var date: String,
    @SerializedName("time") var time: String,
    @SerializedName("place") var place: String,
    @SerializedName("placeLat") val placeLat: Double,
    @SerializedName("placeLng") val placeLng: Double
)
