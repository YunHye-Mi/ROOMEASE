package com.example.capstonedesign2.ui.chat

import com.example.capstonedesign2.data.remote.ChatRoomList
import com.example.capstonedesign2.data.remote.ChatRoomResult
import com.example.capstonedesign2.data.remote.SubscribeChatResponse

interface ChatView {
    fun onCreateChatSuccess(roomResult: ChatRoomResult)
    fun onCreateChatFailure(code: Int, message: String)

    fun onChatSuccess(roomResult: ArrayList<ChatRoomList>?)
    fun onChatFailure(code: Int, message: String)

    fun onBeforeChatSuccess(chatList: ArrayList<SubscribeChatResponse>)
    fun onBeforeChatFailure(code: Int, message: String)
}