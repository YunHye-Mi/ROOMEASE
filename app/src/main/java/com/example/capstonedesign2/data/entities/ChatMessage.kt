package com.example.capstonedesign2.data.entities

import androidx.room.Entity

@Entity(tableName = "ChatMessageTable")
data class ChatMessage(
    var sender: User,
    var receiver: User,
    var message: String,
    var timeStamp: Long
)
