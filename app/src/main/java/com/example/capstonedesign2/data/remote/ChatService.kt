package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.ui.chat.ChatView
import com.example.capstonedesign2.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatService() {
    private lateinit var chatView: ChatView

    fun setChatView(chatView: ChatView) {
        this.chatView = chatView
    }

    fun createChatRoom(authorization: String, brokerId: BrokerIdRequest) {
        val chatService = getRetrofit().create(ChatRetrofitInterface::class.java)
        chatService.createChatRoom("Bearer $authorization", brokerId).enqueue(object: Callback<CreateRoomResponse>{
            override fun onResponse(
                call: Call<CreateRoomResponse>,
                response: Response<CreateRoomResponse>
            ) {
                if (response.isSuccessful) {
                    var resp: CreateRoomResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            chatView.onCreateChatSuccess(resp.chatRoomResult)
                        } else {
                            chatView.onCreateChatFailure(resp.status, resp.message)
                        }
                    }
                    Log.d("LinkChatApi", "채팅 api 연결 성공")
                }
            }

            override fun onFailure(call: Call<CreateRoomResponse>, t: Throwable) {
                Log.d("ChatRoom/Failure", t.message.toString())
            }
        })
    }

    fun getChatRoom(authorization: String) {
        var chatService = getRetrofit().create(ChatRetrofitInterface::class.java)
        chatService.getChatRoom("Bearer $authorization").enqueue(object : Callback<ChatRoomResultResponse> {
            override fun onResponse(call: Call<ChatRoomResultResponse>, response: Response<ChatRoomResultResponse>) {
                if (response.isSuccessful) {
                    var resp: ChatRoomResultResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            chatView.onChatSuccess(resp.chatRoomResult)
                        } else {
                            when (resp.status) {
                                401 -> chatView.onChatFailure(resp.status, resp.message)
                                else -> chatView.onChatFailure(resp.status, resp.message)
                            }
                        }
                    }
                    Log.d("LinkChatApi", "채팅 api 연결 성공")
                }
            }

            override fun onFailure(call: Call<ChatRoomResultResponse>, t: Throwable) {
                Log.d("ChatRoom/Failure", t.message.toString())
            }
        })
    }

    fun getBeforeChat(authorization: String, chatRoomId: Int) {
        var chatService = getRetrofit().create(ChatRetrofitInterface::class.java)
        chatService.getBeforeChat(authorization, chatRoomId).enqueue(object : Callback<BeforeChatResponse> {
            override fun onResponse(call: Call<BeforeChatResponse>, response: Response<BeforeChatResponse>) {
                if (response.isSuccessful) {
                    var resp: BeforeChatResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            chatView.onBeforeChatSuccess(resp.chatRoomResult as ArrayList<SubscribeChatResponse>)
                        } else {
                            when (resp.status) {
                                401 -> chatView.onChatFailure(resp.status, resp.message)
                                else -> chatView.onChatFailure(resp.status, resp.message)
                            }
                        }
                    }
                    Log.d("LinkChatApi", "채팅 api 연결 성공")
                }
            }

            override fun onFailure(call: Call<BeforeChatResponse>, t: Throwable) {
                Log.d("BeforeChat/Failure", t.message.toString())
            }
        })
    }
}