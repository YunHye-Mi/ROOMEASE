package com.example.capstonedesign2.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.entities.ChatMessage
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.databinding.ItemMessageMeBinding
import com.example.capstonedesign2.databinding.ItemMessageOtherBinding
import com.google.gson.Gson

class MessageListAdapter(private val messageList: ArrayList<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val message_sent = 1
    private val message_received = 2
    lateinit var meBinding: ItemMessageMeBinding
    lateinit var otherBinding: ItemMessageOtherBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == message_sent) {
            meBinding = ItemMessageMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            SentMessageViewHolder(meBinding.root)
        } else {
            otherBinding = ItemMessageOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            ReceivedMessageViewHolder(otherBinding.root)
        }
    }

    override fun getItemCount(): Int = messageList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var chatMessage = messageList[position]

        when (holder.itemViewType) {
            message_sent -> (holder as SentMessageViewHolder).bind(chatMessage)
            else -> (holder as ReceivedMessageViewHolder).bind(chatMessage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        var chatMessage = messageList[position]
        var gson = Gson()
        var user = gson.fromJson("currentUser", User::class.java)
        if (chatMessage.sender.id == user.id) {
            return message_sent
        } else {
            return message_received
        }
    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var binding : ItemMessageOtherBinding

        fun bind(chatMessage: ChatMessage) {
            super.itemView

            binding.messageTv.text = chatMessage.message
            binding.timeTv.text = chatMessage.timeStamp.toString()
        }
    }

    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var binding : ItemMessageMeBinding

        fun bind(chatMessage: ChatMessage) {
            super.itemView

            binding.messageTv.text = chatMessage.message
            binding.timeTv.text = chatMessage.timeStamp.toString()
        }
    }
}