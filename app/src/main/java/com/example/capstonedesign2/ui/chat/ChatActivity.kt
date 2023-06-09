package com.example.capstonedesign2.ui.chat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.ActivityChatBinding
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.gson.Gson
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import okhttp3.*
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.StompCommand
import ua.naiksoftware.stomp.dto.StompHeader
import ua.naiksoftware.stomp.dto.StompMessage
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity(), ChatView, RefreshView, ReminderView {
    lateinit var binding : ActivityChatBinding
    lateinit var messageListAdapter: MessageListAdapter
    private var roomId = 0
    private var messageList = ArrayList<ChatMessage>()
    private var gson = Gson()
    private val chatView = ChatService()
    private val authService = AuthService()
    lateinit var user: User
    lateinit var stompClient: StompClient
    private val reminderService = ReminderService()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authService.setRefreshView(this)
        chatView.setChatView(this)
        reminderService.setReminderView(this)

        val spf = getSharedPreferences("reminder", MODE_PRIVATE)
        if (spf != null) {
            binding.noticeLl.visibility = View.VISIBLE
        } else {
            binding.noticeLl.visibility = View.GONE
        }

        val userSpf = getSharedPreferences("currentUser", MODE_PRIVATE)
        val userJson = userSpf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)

        messageListAdapter = MessageListAdapter(messageList, user.accessToken)
        binding.messageRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.messageRv.adapter = messageListAdapter

        roomId = intent.getIntExtra("chatRoomId", 0)
        binding.roomNameTv.text = intent.getStringExtra("brokerName")
        Log.d("ChatRoomId", roomId.toString())
        chatView.getBeforeChat(user.accessToken, roomId)
        reminderService.getReminder(user.accessToken, roomId.toLong())

        val url = "ws://3.39.130.73:8080/ws-stomp/websocket"

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        val stompHeaders = StompHeader("Authorization", "Bearer ${user.accessToken}")
        val headers = mutableListOf<StompHeader>(stompHeaders)
        stompClient.connect(headers)

        val subscription = stompClient.topic("/sub/channel/$roomId", headers)
            .subscribe {
                runOnUiThread {
                    val message = gson.fromJson(it.payload, ChatMessage::class.java)
                    Log.i("message receive", it.payload.toString())
                    messageList.add(message)
                    messageListAdapter.notifyDataSetChanged()
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
                    binding.sendIv.setOnClickListener {
                        val data = ChatRequest(user.accessToken, p0.toString(), roomId)
                        val message = gson.toJson(data)
                        binding.msgEt.text.clear()

                        //send
                        val sendHeader = StompHeader(StompHeader.DESTINATION, "/pub/chat/msg")
                        val sendHeaderList = mutableListOf(sendHeader, stompHeaders)
                        stompClient.send(
                            StompMessage(
                                StompCommand.MESSAGE,
                                sendHeaderList,
                                message
                            )
                        ).subscribe()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        onClickListener()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()

        reminderService.getReminder(user.accessToken, roomId.toLong())
    }

    override fun onResume() {
        super.onResume()

        reminderService.getReminder(user.accessToken, roomId.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()

        stompClient.disconnect()
    }

    private fun onClickListener() {
        binding.backIv.setOnClickListener {
            finish()
        }

        binding.noticeLl.setOnClickListener {
            val intent = Intent(this, SeeReminderActivity::class.java)
            startActivity(intent)
        }

        binding.addReminderIv.setOnClickListener {
            val intent = Intent(this, ReminderActivity::class.java)
            intent.putExtra("chatRoomId", roomId)
            startActivity(intent)
        }
    }

    override fun onCreateChatSuccess(roomResult: ChatRoomResult) {
        TODO("Not yet implemented")
    }

    override fun onCreateChatFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onChatSuccess(roomResult: ArrayList<ChatRoomResult>?) {
        TODO("Not yet implemented")
    }

    override fun onChatFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBeforeChatSuccess(chatList: ArrayList<ChatMessage>?) {
        if (chatList != null) {
            for (i in chatList) {
                messageList.add(i)
                Log.d("BeforeMessage", "${chatList.size}")
            }
            messageListAdapter.notifyDataSetChanged()
        }
        Log.d("BeforeMessage", "이전 메시지 가져오기 성공")
    }

    override fun onBeforeChatFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("BeforeChat/Failure", "$code/$message")
                authService.refresh(RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("BeforeChat/Failure", "$code/$message")
        }
    }

    override fun onRefreshSuccess(accessToken: String, refreshToken: String) {
        val updateUser = User(accessToken, refreshToken, user.nickname, user.registerNumber, user.role)
        val gson = Gson()
        val userJson = gson.toJson(updateUser)
        val userSpf = getSharedPreferences("currentUser", MODE_PRIVATE)
        val editor = userSpf.edit()
        editor.apply {
            putString("User", userJson)
        }

        editor.apply()

        chatView.getBeforeChat(accessToken, roomId)
        reminderService.getReminder(accessToken, roomId.toLong())

        Log.d("ReGetRoom", "${updateUser.accessToken}/${updateUser.refreshToken}")
    }

    override fun onRefreshFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Refresh/Failure", "$code/$message")
                authService.refresh(RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("Refresh/Failure", "$code/$message")
        }
    }

    override fun onReminderSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onReminderFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onSeeReminderSuccess(reminder: SeeReminder?) {
        if (reminder != null) {
            val spf = getSharedPreferences("reminder", MODE_PRIVATE)
            val editor = spf.edit()
            val reminderJson = gson.toJson(reminder)
            editor.apply {
                putString("seeReminder", reminderJson)
            }
            editor.apply()
            binding.noticeLl.visibility = View.VISIBLE
        } else {
            binding.noticeLl.visibility = View.GONE
        }
    }

    override fun onSeeReminderFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("GetReminder/Failure", "$code/$message")
                authService.refresh(
                    RefreshRequest(user.refreshToken)
                )
            }
            403 -> Log.d("GetReminder/Failure", "$code/$message")
        }
    }
}
