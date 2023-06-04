package com.example.capstonedesign2.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.remote.ChatRoomList
import com.example.capstonedesign2.data.remote.ChatRoomResult
import com.example.capstonedesign2.databinding.ItemChatroomBinding
import kotlin.collections.ArrayList

class ChatRVAdapter(private val roomList: ArrayList<ChatRoomList>) : RecyclerView.Adapter<ChatRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(room: ChatRoomList)
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
        fun bind(room: ChatRoomList) {
            binding.intermediaryTv.text = room.broker
            binding.currentChatTv.text = room.lastMessage
        }
    }
}