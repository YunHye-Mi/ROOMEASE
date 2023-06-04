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
import com.beust.klaxon.Klaxon
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.ActivityChatBinding
import com.example.capstonedesign2.ui.login.RefreshView
import com.gmail.bishoybasily.stomp.lib.Event
import com.gmail.bishoybasily.stomp.lib.StompClient
import com.google.gson.Gson
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class ChatActivity : AppCompatActivity(), ChatView, RefreshView, ReminderView {
    lateinit var binding : ActivityChatBinding
    lateinit var messageListAdapter: MessageListAdapter
    private var roomId = 0
    private var messageList = ArrayList<SubscribeChatResponse>()
    private var gson = Gson()
    private val chatView = ChatService()
    private val authService = AuthService()
    lateinit var user: User
    lateinit var stompConnection: Disposable
    lateinit var topic: Disposable
    private var constant: Constant = Constant
    var jsonObject = JSONObject()
    private val reminderService = ReminderService()
    @RequiresApi(Build.VERSION_CODES.O)
    private var localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chatView.setChatView(this)
        reminderService.setReminderView(this)

        var spf = getSharedPreferences("reminder", MODE_PRIVATE)
        if (spf != null) {
            binding.noticeLl.visibility = View.VISIBLE
        } else {
            binding.noticeLl.visibility = View.GONE
        }

        val userSpf = getSharedPreferences("currentUser", MODE_PRIVATE)
        val userJson = userSpf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)

        messageListAdapter = MessageListAdapter(messageList)
        binding.messageRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.messageRv.adapter = messageListAdapter

        topic = Disposables.empty()

        roomId = intent.getIntExtra("chatRoomId", 0)
        Log.d("ChatRoomId", roomId.toString())
        chatView.getBeforeChat(user.accessToken, roomId)
        reminderService.getReminder(user.accessToken, roomId.toLong())

        val url = constant.URL
        val intervalMillis = 5000L
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor {
                val request = it.request().newBuilder()
                    .headers(Headers.headersOf("Authorization: Bearer ${user.accessToken}"))
                    .build()
                it.proceed(request)
            }
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        val stomp = StompClient(client, intervalMillis).apply {
            this.url = url
        }



        stompConnection = stomp.connect().subscribe{
            when (it.type) {
                Event.Type.OPENED -> {
                    Log.d("OPENED", "Stomp opened")
                    topic = stomp.join("/sub/channel/$roomId").subscribe {
                            stompMessage ->
                        val result = Klaxon().parse<SubscribeChatResponse>(stompMessage)
                        this.runOnUiThread {  }
                        if (result != null) {
                            messageList.add(result)
                            messageListAdapter.notifyDataSetChanged()
                        }
                    }
                    try {
                        jsonObject.put("chatRoomId", roomId)
                        jsonObject.put("content", "안녕하세요")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    stomp.send("/pub/chat/msg", jsonObject.toString()).subscribe()

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
                                    stomp.send("/put/chat/msg", chatRequestJson.toString()).subscribe()
                                    binding.msgEt.text.clear()
                                }
                            }
                        }

                        override fun afterTextChanged(p0: Editable?) {

                        }
                    })

                }
                Event.Type.CLOSED -> {
                    Log.d("CLOSED", "Stomp closed")
                    topic.dispose()
                }
                Event.Type.ERROR -> {
                    Log.d("ERROR", "Stomp error")
                    Log.i("CONNECT ERROR", "연결 오류")
                }
                null -> TODO()
            }
        }
        onClickListener()
    }

    override fun onResume() {
        super.onResume()

        reminderService.getReminder(user.accessToken, roomId = roomId.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()

        topic.dispose()
        stompConnection.dispose()
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

    override fun onChatSuccess(roomResult: ArrayList<ChatRoomList>?) {
        TODO("Not yet implemented")
    }

    override fun onChatFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onBeforeChatSuccess(chatList: ArrayList<SubscribeChatResponse>) {
        for (i in chatList) {
            messageList.add(i)
        }
        messageListAdapter.notifyDataSetChanged()
        Log.d("BeforeMessage", "이전 메시지 가져오기 성공")
    }

    override fun onBeforeChatFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Register/Failure", "$code/$message")
                authService.refresh(user.accessToken, RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("Register/Failure", "$code/$message")
        }
    }

    override fun onRefreshSuccess(accessToken: String, refreshToken: String) {
        val updateUser = User(accessToken, refreshToken, user.nickname, null, "General")
        val gson = Gson()
        val userJson = gson.toJson(updateUser)
        val userSpf = getSharedPreferences("currentUser", MODE_PRIVATE)
        val editor = userSpf.edit()
        editor.apply {
            putString("User", userJson)
        }

        editor.commit()

        chatView.getBeforeChat(accessToken, roomId)
        reminderService.getReminder(accessToken, roomId.toLong())

        Log.d("ReGetRoom", "${updateUser.accessToken}/${updateUser.refreshToken}")
    }

    override fun onRefreshFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Refresh/Failure", "$code/$message")
                authService.refresh(user.accessToken, RefreshRequest(user.refreshToken))
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
                    user.accessToken,
                    RefreshRequest(user.refreshToken)
                )
            }
            403 -> Log.d("GetReminder/Failure", "$code/$message")
        }
    }
}
