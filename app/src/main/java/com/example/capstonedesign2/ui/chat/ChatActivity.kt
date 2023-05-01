package com.example.capstonedesign2.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.capstonedesign2.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (binding.msgEt.text.isNotEmpty()) {
            binding.sendIv.visibility = View.VISIBLE
        } else {
            binding.sendIv.visibility = View.GONE
        }

        onClickListener()
    }

    private fun onClickListener() {
        binding.sendIv.setOnClickListener {

        }
    }
}
