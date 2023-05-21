package com.example.capstonedesign2.ui.chat

import com.example.capstonedesign2.data.remote.ChatResponse
import com.example.capstonedesign2.data.remote.ChatRoomResult

interface ChatView {
    fun onChatSuccess(roomResult: ArrayList<ChatRoomResult>?)
    fun onChatFailure(message: String)
}