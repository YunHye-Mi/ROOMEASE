package com.example.capstonedesign2.data.entities

data class ChatRoom(
    var int: Int,
    var clientId : String?,
    var intermediaryId : String?,
    var estate_name : String?,
    var messages: MutableList<ChatMessage>
)
