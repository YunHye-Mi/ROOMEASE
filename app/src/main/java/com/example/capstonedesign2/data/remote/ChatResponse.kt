package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName

data class ChatRoomResultResponse(
    @SerializedName("status") val  status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val chatRoomResult: ArrayList<ChatRoomResult>?
)

data class ChatRequest(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("content") val content: String,
    @SerializedName("chatRoomId") val chatRoomId: Int
)

data class BrokerIdRequest(
    @SerializedName("brokerId") val brokerId: Int
)

data class CreateRoomResponse(
    @SerializedName("status") val  status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val chatRoomResult: ChatRoomResult
)

data class ChatRoomResult(
    @SerializedName("id") val id: Int,
    @SerializedName("opponent") val broker: String,
    @SerializedName("lastMessage") val lastMessage: String?,
    @SerializedName("lastMessageTimestamp") val lastMessageTimestamp: String?
)

data class BeforeChatResponse(
    @SerializedName("status") val  status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val chatMessage: ArrayList<ChatMessage>
)

data class ChatMessage(
    @SerializedName("id") val id: Int,
    @SerializedName("sender") var sender: String,
    @SerializedName("userId") var userId: Int,
    @SerializedName("accessToken") var accessToken: String,
    @SerializedName("content") var message: String,
    @SerializedName("timestamp") var timeStamp: String,
    @SerializedName("chatRoomId") var chatRoomId: Int
)

