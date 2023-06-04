package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName

data class Reminder(
    @SerializedName("chatRoomId") var roomId: Int,
    @SerializedName("date") var date: String,
    @SerializedName("time") var time: String,
    @SerializedName("place") var place: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("latitude") val latitude: String
)

data class ReminderResponse(
    @SerializedName("status") val  status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: SeeReminder?
)

data class SeeReminder(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("chatRoomId") var roomId: Int,
    @SerializedName("date") var date: String,
    @SerializedName("time") var time: String,
    @SerializedName("place") var place: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("latitude") val latitude: String
)
