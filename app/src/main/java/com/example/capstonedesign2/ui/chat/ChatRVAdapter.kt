package com.example.capstonedesign2.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.entities.ChatRoom
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.databinding.ItemChatroomBinding
import com.example.capstonedesign2.databinding.ItemResultBinding
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ChatRVAdapter(private val roomList: ArrayList<ChatRoom>) : RecyclerView.Adapter<ChatRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(room: ChatRoom)
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
        fun bind(room: ChatRoom) {
            binding.intermediaryTv.text = room.receiver.nickname
            binding.currentChatTv.text = room.messages.last().message
        }
    }
}