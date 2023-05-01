package com.example.capstonedesign2.data.entities

data class ChatMessage(
    var senderId: String,
    var receiverId: String,
    var message: String,
    var timeStamp: Long
)
