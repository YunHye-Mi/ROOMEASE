package com.example.capstonedesign2.data.entities

import com.google.gson.annotations.SerializedName

data class ChatRoom(
    @SerializedName("roomId") var roomId: Int,
    @SerializedName("sender") var sender : User,
    @SerializedName("receiver") var receiver : User,
    @SerializedName("messages") var messages: MutableList<ChatMessage>
)
