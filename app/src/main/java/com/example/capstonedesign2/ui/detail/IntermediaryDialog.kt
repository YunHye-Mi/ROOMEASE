package com.example.capstonedesign2.ui.detail

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.ChatResponse
import com.example.capstonedesign2.data.remote.ChatRoomResult
import com.example.capstonedesign2.data.remote.ChatService
import com.example.capstonedesign2.databinding.DialogIntermediaryBinding
import com.example.capstonedesign2.ui.chat.ChatActivity
import com.example.capstonedesign2.ui.chat.ChatView
import com.google.gson.Gson

class IntermediaryDialog(context: Context) : Dialog(context), ChatView {
    lateinit var binding: DialogIntermediaryBinding
    lateinit var chatView: ChatService
    private var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogIntermediaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var spf = context.getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
        var userJson = spf.getString("User", "")
        var user = gson.fromJson(userJson, User::class.java)
        var accessToken = ""
        if (userJson != null) {
            accessToken = user.token
        }

        chatView.setChatView(this)

        binding.dialogChatTv.setOnClickListener {
            dismiss()
            chatView.createChatRoom(accessToken, 0) // 추후 수정 필요
            var intent = Intent(context, ChatActivity::class.java)
            context.startActivity(intent)
        }

        binding.dialogReviewTv.setOnClickListener {
            dismiss()
            var intent = Intent(context, IntermediaryReviewActivity::class.java)
            context.startActivity(intent)
        }

        binding.closeIv.setOnClickListener {
            dismiss()
        }
    }

    override fun onChatSuccess(roomResult: ArrayList<ChatRoomResult>?) {
        Log.d("CHAT/SUCCESS", "ChatRoom Created")
    }

    override fun onChatFailure(message: String) {
        Log.d("CHAT/FAILURE", "ChatRoom Not Created")
    }
}