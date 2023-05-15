package com.example.capstonedesign2.ui.chat

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.entities.ChatRoom
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.databinding.FragmentChatBinding
import com.google.gson.Gson

class ChatFragment() : Fragment() {
    lateinit var binding: FragmentChatBinding
    private var roomList = ArrayList<ChatRoom>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        var userSpf = this.requireContext().getSharedPreferences("User", AppCompatActivity.MODE_PRIVATE)
        var currentUser = userSpf.getString("user", "")
        var chatRVAdapter = ChatRVAdapter(roomList)
        binding.chatRoomRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.chatRoomRv.adapter = chatRVAdapter

        var intent = Intent(this.context, ChatActivity::class.java)

        chatRVAdapter.setMyItemClickListener(object : ChatRVAdapter.MyItemClickListener {
            override fun onItemClick(room: ChatRoom) {
                var editor = userSpf.edit()
                editor.apply {
                    putString("currentUser", currentUser)
                }
                editor.commit()
                startActivity(intent)
            }
        })

        return binding.root
    }
}
