package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ChatResponse(
    @SerializedName("status") val  status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val chatRoomResult: List<ChatRoomResult>?
)

data class ChatRequest(
    @SerializedName("content") var content: String,
    @SerializedName("chatRoomId") var chatRoomId: Int
)

data class ChatRoomResult(
    @SerializedName("id") val id: Int,
    @SerializedName("broker") val broker: String,
    @SerializedName("lastMessage") val lastMessage: String?,
    @SerializedName("lastMessageTimestamp") val lastMessageTimestamp: LocalDateTime
)

data class SubscribeChatResponse(
    @SerializedName("sender") var sender: String,
    @SerializedName("sessionId") var sessionId: Int,
    @SerializedName("content") var content: String,
    @SerializedName("timestamp") var timestamp: LocalDateTime,
    @SerializedName("chatRoomId") var chatRoomId: Int
)