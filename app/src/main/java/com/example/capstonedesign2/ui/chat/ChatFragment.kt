package com.example.capstonedesign2.ui.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.ChatRoomResult
import com.example.capstonedesign2.data.remote.ChatService
import com.example.capstonedesign2.databinding.FragmentChatBinding
import com.google.gson.Gson

class ChatFragment() : Fragment(), ChatView {
    lateinit var binding: FragmentChatBinding
    lateinit var chatRVAdapter: ChatRVAdapter
    lateinit var chatView: ChatService
    private var roomList = ArrayList<ChatRoomResult>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        chatView.setChatView(this)

        var userSpf = this.requireContext().getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
        var currentUser = userSpf.getString("user", "")
        var gson = Gson()
        var user = gson.fromJson(currentUser, User::class.java)

        chatView.getChatRoom(user.token)

        chatRVAdapter = ChatRVAdapter(roomList)
        binding.chatRoomRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.chatRoomRv.adapter = chatRVAdapter

        var intent = Intent(this.context, ChatActivity::class.java)

        chatRVAdapter.setMyItemClickListener(object : ChatRVAdapter.MyItemClickListener {
            override fun onItemClick(room: ChatRoomResult) {
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

    override fun onChatSuccess(roomResult: ArrayList<ChatRoomResult>?) {
        if (roomResult != null) {
            for (i in roomResult)
                roomList.add(i)
            chatRVAdapter.notifyDataSetChanged()
        }

    }

    override fun onChatFailure(message: String) {
        Log.d("CHAT/FAILURE", message)
    }
}
