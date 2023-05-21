package com.example.capstonedesign2.ui.chat

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.ChatRequest
import com.example.capstonedesign2.data.remote.SubscribeChatResponse
import com.example.capstonedesign2.databinding.ActivityChatBinding
import com.google.gson.Gson
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompHeader
import java.time.LocalDateTime
import java.time.ZoneId

class ChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatBinding
    lateinit var userJson: String
    lateinit var stompClient: StompClient
    lateinit var messageListAdapter: MessageListAdapter
    private var messageList = ArrayList<SubscribeChatResponse>()
    private var headerList = ArrayList<StompHeader>()
    private var gson = Gson()
    lateinit var currentUser: User
    @RequiresApi(Build.VERSION_CODES.O)
    private var localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var spf = getSharedPreferences("reminder", MODE_PRIVATE)
        if (spf != null) {
            binding.noticeLl.visibility = View.VISIBLE
        } else {
            binding.noticeLl.visibility = View.GONE
        }

        userJson = intent.getStringExtra("currentUser").toString()

//        initRv()
//
//        runStomp(0, currentUser.id)

        onClickListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        stompClient.disconnect()
    }

    private fun runStomp(roomId: Int, userId: Int) {
        val url = "3.39.130.73:8080"
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://$url/ws-stomp/sub/channel/$roomId")

        currentUser = gson.fromJson(userJson, User::class.java)
        var accessToken = currentUser.token
        headerList.add(StompHeader("Authorization", "Barer $accessToken"))
        stompClient.connect(headerList)

        stompClient.lifecycle().subscribe{ lifecycleEvent ->
            when (lifecycleEvent.type) {
                LifecycleEvent.Type.OPENED -> {
                    Log.d("OPENED", "Stomp opened")
                }
                LifecycleEvent.Type.CLOSED -> {
                    Log.d("CLOSED", "Stomp closed")
                }
                LifecycleEvent.Type.ERROR -> {
                    Log.d("ERROR", "Stomp error")
                    Log.i("CONNECT ERROR", lifecycleEvent.exception.toString())
                }
                else -> {
                    Log.d("else", lifecycleEvent.message)
                }
            }
        }

        binding.msgEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    binding.sendIv.visibility = View.GONE
                } else {
                    binding.sendIv.visibility = View.VISIBLE
                    var chatRequestJson = gson.toJson(ChatRequest(p0.toString(), roomId))
                    binding.sendIv.setOnClickListener {
                        stompClient.send("ws://$url/ws-stomp/pub/chat/msg", chatRequestJson).subscribe()
                        stompClient.topic("ws://$url/ws-stomp/sub/channel/$roomId").subscribe {
                            var messageJson = it.payload
                            var message = gson.fromJson(messageJson, SubscribeChatResponse::class.java)
                            messageList.add(message)
                            messageListAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun initRv() {
        messageListAdapter = MessageListAdapter(messageList)
        binding.messageRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.messageRv.adapter = messageListAdapter
    }

    private fun onClickListener() {
        binding.backIv.setOnClickListener {
            finish()
        }

        binding.noticeLl.setOnClickListener {
            var intent = Intent(this, SeeReminderActivity::class.java)
            startActivity(intent)
        }

        binding.addReminderIv.setOnClickListener {
            var intent = Intent(this, ReminderActivity::class.java)
            startActivity(intent)
        }
    }
}
