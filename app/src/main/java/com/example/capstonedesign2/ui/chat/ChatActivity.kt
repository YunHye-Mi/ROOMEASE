package com.example.capstonedesign2.ui.chat

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.capstonedesign2.data.entities.ChatMessage
import com.example.capstonedesign2.databinding.ActivityChatBinding
import com.google.gson.Gson

class ChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatBinding
    lateinit var userJson: String
    private var messageList = ArrayList<ChatMessage>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userJson = intent.getStringExtra("currentUser").toString()

        var messageListAdapter = MessageListAdapter(messageList)


        binding.msgEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    binding.sendIv.visibility = View.GONE
                } else {
                    binding.sendIv.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        onClickListener()
    }
    private fun onClickListener() {
        binding.sendIv.setOnClickListener {

        }

        binding.backIv.setOnClickListener {
            onBackPressed()
        }
    }
}
