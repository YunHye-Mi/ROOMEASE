package com.example.capstonedesign2.data.remote

import com.example.capstonedesign2.ui.chat.ChatView
import com.example.capstonedesign2.utils.getRetrofit
import retrofit2.Call
import retrofit2.Response

class ChatService() {
    private lateinit var chatView: ChatView

    fun setChatView(chatView: ChatView) {
        this.chatView = chatView
    }

    fun createChatRoom(authorization: String, brokerId: Int) {
        val chatService = getRetrofit().create(ChatRetrofitInterface::class.java)
        chatService.createChatRoom("Bearer $authorization", brokerId).enqueue(object: retrofit2.Callback<ChatResponse>{
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                var resp = response.body()
                when (resp?.status) {

                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {

            }

        })
    }

    fun getChatRoom(authorization: String) {
        var chatService = getRetrofit().create(ChatRetrofitInterface::class.java)
        chatService.getChatRoom("Bearer $authorization").enqueue(object : retrofit2.Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                var resp = response.body()
                when (resp?.status) {
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {

            }

        })
    }

}