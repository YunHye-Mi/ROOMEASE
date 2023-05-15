package com.example.capstonedesign2.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ChatRoomTable")
data class ChatRoom(
    @PrimaryKey(autoGenerate = true) var roomId: Int,
    var sender : User,
    var receiver : User,
    var messages: MutableList<ChatMessage>
)
