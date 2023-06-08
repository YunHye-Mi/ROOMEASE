package com.example.capstonedesign2.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.remote.ChatRoomResult
import com.example.capstonedesign2.databinding.ItemChatroomBinding
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class ChatRVAdapter(private val roomList: ArrayList<ChatRoomResult>) : RecyclerView.Adapter<ChatRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(room: ChatRoomResult)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemChatroomBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(roomList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(roomList[position]) }
    }

    override fun getItemCount(): Int = roomList.size

    inner class ViewHolder(val binding: ItemChatroomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(room: ChatRoomResult) {
            binding.intermediaryTv.text = room.broker
            binding.currentChatTv.text = room.lastMessage
            val localDateTime = LocalDateTime.parse(room.lastMessageTimestamp)
            binding.lastMessageTimeTv.text = localDateTime.format(DateTimeFormatter.ofPattern("yy년 M월 d일 a h:mm"))
        }
    }
}