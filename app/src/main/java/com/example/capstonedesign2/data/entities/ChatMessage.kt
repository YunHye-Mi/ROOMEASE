package com.example.capstonedesign2.data.entities

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ChatMessage(
    @SerializedName("id") val id: Int,
    @SerializedName("sender") var sender: String,
    @SerializedName("userId") var userId: Int,
    @SerializedName("content") var message: String,
    @SerializedName("timestamp") var timeStamp: LocalDateTime,
    @SerializedName("chatRoomId") var chatRoomId: Int
)
