package com.example.capstonedesign2.ui.detail

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.DialogIntermediaryBinding
import com.example.capstonedesign2.ui.chat.ChatActivity
import com.example.capstonedesign2.ui.chat.ChatView
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.gson.Gson

class BrokerDialog(context: Context, val id: Int) : Dialog(context), ChatView, BrokerReviewView, RefreshView {
    lateinit var binding: DialogIntermediaryBinding
    lateinit var chatView: ChatService
    lateinit var user: User
    private var brokerReviewView = ReviewService()
    private var authService = AuthService()
    private var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogIntermediaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("BrokerId", id.toString())

        var spf = context.getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
        var userJson = spf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)
        var accessToken = ""
        if (userJson != null) {
            accessToken = user.accessToken
        }

        chatView = ChatService()
        chatView.setChatView(this)
        brokerReviewView.setBrokerReviewView(this)

        binding.dialogChatTv.setOnClickListener {
            dismiss()
            chatView.createChatRoom(accessToken, BrokerIdRequest(id))
        }

        binding.dialogReviewTv.setOnClickListener {
            dismiss()
            var intent = Intent(context, BrokerReviewActivity::class.java)
            intent.putExtra("brokerId", id)
            context.startActivity(intent)
        }

        binding.closeIv.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateChatSuccess(roomResult: ChatRoomResult) {
        var intent = Intent(context, ChatActivity::class.java)
        intent.putExtra("chatRoomId", roomResult.id)
        context.startActivity(intent)
        Log.d("CHAT/SUCCESS", "ChatRoom Created")
    }

    override fun onCreateChatFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("CHAT/FAILURE", "$code/$message")
                authService.refresh(RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("CHAT/FAILURE", "$code/$message")
        }
    }

    override fun onChatSuccess(roomResult: ArrayList<ChatRoomResult>?) {

    }

    override fun onChatFailure(code: Int, message: String) {

    }

    override fun onBeforeChatSuccess(chatList: ArrayList<ChatMessage>?) {
        TODO("Not yet implemented")
    }

    override fun onAddBrokerReviewSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onAddBrokerReviewFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onBrokerReviewSuccess(brokerScore: BrokerScore?) {

    }

    override fun onBrokerReviewFailure(code: Int, message: String) {

    }
    override fun onBeforeChatFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onRefreshSuccess(accessToken: String, refreshToken: String) {
        if (isShowing) {
            val updateUser = User(accessToken, refreshToken, user.nickname, user.registerNumber, user.role)
            val gson = Gson()
            val userJson = gson.toJson(updateUser)
            val userSpf = context.getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
            val editor = userSpf.edit()
            editor.apply {
                putString("User", userJson)
            }

            editor.apply()

            chatView.createChatRoom(user.accessToken, BrokerIdRequest(id))

            Log.d("ReGetReview", "${updateUser.accessToken}/${updateUser.role}")

        }
    }

    override fun onRefreshFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Refresh/Failure", "$code/$message")
                authService.refresh(RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("Refresh/Failure", "$code/$message")
        }
    }
}