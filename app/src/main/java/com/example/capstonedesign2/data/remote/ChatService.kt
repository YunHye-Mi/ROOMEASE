package com.example.capstonedesign2.data.remote

import android.util.Log
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
                if (response.isSuccessful) {
                    var resp: ChatResponse? = response.body()
                    if (resp != null) {
                        Log.d("CHAT/SUCCESS", response.message())
                        chatView.onChatSuccess(resp.chatRoomResult as ArrayList<ChatRoomResult>?)
                    }
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                chatView.onChatFailure(t.message.toString())
            }
        })
    }

    fun getChatRoom(authorization: String) {
        var chatService = getRetrofit().create(ChatRetrofitInterface::class.java)
        chatService.getChatRoom("Bearer $authorization").enqueue(object : retrofit2.Callback<ChatResponse> {
            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
                if (response.isSuccessful) {
                    var resp: ChatResponse? = response.body()
                    if (resp != null) {
                        Log.d("CHAT/SUCCESS", response.message())
                        chatView.onChatSuccess(null)
                    }
                }
            }

            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
                chatView.onChatFailure(t.message.toString())
            }
        })
    }
}