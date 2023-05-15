package com.example.capstonedesign2.ui.detail

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.capstonedesign2.databinding.DialogIntermediaryBinding
import com.example.capstonedesign2.ui.chat.ChatActivity

class IntermediaryDialog(context: Context) : Dialog(context) {
    lateinit var binding: DialogIntermediaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogIntermediaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dialogChatTv.setOnClickListener {
            dismiss()
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
}