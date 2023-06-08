package com.example.capstonedesign2.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.remote.ChatMessage
import com.example.capstonedesign2.databinding.ItemMessageMeBinding
import com.example.capstonedesign2.databinding.ItemMessageOtherBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MessageListAdapter(private val messageList: ArrayList<ChatMessage>, private val accessToken: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        return if (chatMessage.accessToken == accessToken) {
            message_sent
        } else {
            message_received
        }
    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(chatMessage: ChatMessage) {
            super.itemView

            otherBinding.messageTv.text = chatMessage.message
            otherBinding.timeTv.text = LocalDateTime.parse(chatMessage.timeStamp).format(DateTimeFormatter.ofPattern("a h:mm"))
        }
    }

    inner class SentMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(chatMessage: ChatMessage) {
            super.itemView
            meBinding.messageTv.text = chatMessage.message
            meBinding.timeTv.text = LocalDateTime.parse(chatMessage.timeStamp).format(DateTimeFormatter.ofPattern("a h:mm"))
        }
    }
}