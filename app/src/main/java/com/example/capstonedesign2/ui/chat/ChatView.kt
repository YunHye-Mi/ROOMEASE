package com.example.capstonedesign2.ui.chat

import com.example.capstonedesign2.data.remote.ChatRoomResult
import com.example.capstonedesign2.data.remote.ChatMessage

interface ChatView {
    fun onCreateChatSuccess(roomResult: ChatRoomResult)
    fun onCreateChatFailure(code: Int, message: String)

    fun onChatSuccess(roomResult: ArrayList<ChatRoomResult>?)
    fun onChatFailure(code: Int, message: String)

    fun onBeforeChatSuccess(chatList: ArrayList<ChatMessage>?)
    fun onBeforeChatFailure(code: Int, message: String)
}