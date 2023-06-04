package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ChatRoomResultResponse(
    @SerializedName("status") val  status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val chatRoomResult: ArrayList<ChatRoomList>?
)

data class ChatRequest(
    @SerializedName("content") var content: String,
    @SerializedName("chatRoomId") var chatRoomId: Int
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

data class ChatRoomList(
    @SerializedName("id") val id: Int,
    @SerializedName("opponent") val broker: String,
    @SerializedName("lastMessage") val lastMessage: String?,
    @SerializedName("lastMessageTimestamp") val lastMessageTimestamp: LocalDateTime
)

data class ChatRoomResult(
    @SerializedName("id") val id: Int,
    @SerializedName("broker") val broker: String,
    @SerializedName("lastMessage") val lastMessage: String?,
    @SerializedName("lastMessageTimestamp") val lastMessageTimestamp: String
)

data class BeforeChatResponse(
    @SerializedName("status") val  status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val chatRoomResult: List<SubscribeChatResponse>
)

data class SubscribeChatResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("sender") var sender: String,
    @SerializedName("userId") var userId: Int,
    @SerializedName("myMessage") var myMessage: Boolean,
    @SerializedName("content") var message: String,
    @SerializedName("timestamp") var timeStamp: LocalDateTime,
    @SerializedName("chatRoomId") var chatRoomId: Int
)