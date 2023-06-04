package com.example.capstonedesign2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.ActivityDetailBinding
import com.example.capstonedesign2.ui.bookmark.BookmarkView
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class DetailActivity : AppCompatActivity(), RoomView, BookmarkView, RefreshView, BrokerReviewView {
    lateinit var binding : ActivityDetailBinding
    private val information = arrayListOf("대중교통", "주변환경", "리뷰")
    private var gson = Gson()
    private var bookmarkView = BookmarkService()
    private var roomView = RoomService()
    private var brokerReviewView = ReviewService()
    private val authService = AuthService()
    private var roomId = ""
    private var brokerId = 0
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookmarkView.setBookmarkView(this)
        roomView.setRoomView(this)
        brokerReviewView.setBrokerReviewView(this)
        authService.setRefreshView(this)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookmarkOn = intent.getBooleanExtra("bookmarkOn", false)

        if (bookmarkOn) {
            binding.bookmarkOffIv.visibility = View.GONE
            binding.bookmarkOnIv.visibility = View.VISIBLE
        } else {
            binding.bookmarkOffIv.visibility = View.VISIBLE
            binding.bookmarkOnIv.visibility = View.GONE
        }

        binding.brokerRateIv.visibility = View.GONE

        roomId = intent.getStringExtra("roomId").toString()
        val roomSpf = getSharedPreferences("roomId", MODE_PRIVATE)
        val userSpf = getSharedPreferences("currentUser", MODE_PRIVATE)
        val userJson = userSpf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)

        val editor = roomSpf.edit()
        editor.apply {
            putInt("roomId", roomId.toInt())
        }
        editor.commit()

        if (user.role == "General") {
            binding.bookmarkOnIv.setImageResource(R.drawable.baseline_favorite_24)
            binding.bookmarkOffIv.setImageResource(R.drawable.outline_favorite_border_24)
        } else {
            binding.bookmarkOnIv.setImageResource(R.drawable.baseline_task_alt_24)
            binding.bookmarkOffIv.setImageResource(R.drawable.baseline_add_task_24)
        }

        binding.backIv.setOnClickListener {
            finishAndRemoveTask()
        }

        binding.bookmarkOnIv.setOnClickListener {
            binding.bookmarkOffIv.visibility = View.VISIBLE
            binding.bookmarkOnIv.visibility = View.GONE
        }

        binding.bookmarkOffIv.setOnClickListener {
            binding.bookmarkOffIv.visibility = View.GONE
            binding.bookmarkOnIv.visibility = View.VISIBLE

            if (user.role == "General") {
                if (roomId != null) {
                    bookmarkView.addBookmark(user.accessToken, roomId.toInt())
                }
            } else {
                if (roomId != null) {
                    roomView.addRoom(user.accessToken, roomId.toInt())
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        roomView.getRoomDetail(roomId)
        Log.d("RoomId", roomId)
    }

    override fun onResume() {
        super.onResume()
        brokerReviewView.getBrokerReviews(user.accessToken, brokerId)
    }

    override fun onAddRoomSuccess(message: String) {
        Log.d("ADDBOOKMARK/SUCCESS", message)
    }

    override fun onAddRoomFailure(code: Int, message: String) {
        Log.d("ADDBOOKMARK/FAILURE", message)
    }

    override fun onDetailSuccess(room: ArrayList<Room>) {
        for (i in room) {
            binding.addressTv.text = i.address
            if (i.sales_type == "월세") {
                binding.priceTv.text = "${i.deposit}만원 /${i.rent}만원"
            } else {
                binding.priceTv.text = "${i.deposit}만원"
            }
            binding.manageTv.text = "${i.manage_cost}만원"
            Glide.with(this).load("${i.coverImg}?w=400&h=300").into(binding.detailIv)

            binding.detailAreaTv.text = "${i.size}(m²)"

            val detailAdapter = DetailVPAdapter(this, i)
            binding.detailVp.adapter = detailAdapter

            TabLayoutMediator(binding.detailTb, binding.detailVp){
                    tab, position ->
                tab.text = information[position]

            }.attach()

            for (j in i.brokerInfo) {
                brokerId = j.user_id
                binding.intermediaryNameTv.text = j.name
                brokerReviewView.getBrokerReviews(user.accessToken, brokerId)
                binding.infoIntermediaryLl.setOnClickListener {
                    val customDialog = BrokerDialog(this, brokerId)
                    customDialog.show()
                    customDialog.binding.dialogNameTv.text = binding.intermediaryNameTv.text
                    customDialog.binding.dialogRateTv.text = binding.intermediaryRateTv.text
                }
            }

            Log.d("GETROOM/SUCCESS", "방 정보를 가져왔습니다.")
        }
    }

    override fun onDetailFailure(message: String) {
        Log.d("GETROOM/FAILURE", "방 정보 가져오기 실패/ $message")
    }

    override fun onBookmarkSuccess(message: String) {
        Log.d("BOOKMARK/SUCCESS", "북마크 추가 성공/ $message")
    }

    override fun onBookmarkFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("AddBookmark/Failure", "$code/$message")
                authService.refresh(user.accessToken, RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("AddBookmark/Failure", "$code/$message")
        }
    }

    override fun onBMListSuccess(bookmarkList: ArrayList<EstateInfo>?) {
        TODO("Not yet implemented")
    }

    override fun onBMListFailure(code: Int, message: String) {
        TODO("Not yet implemented")
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

        brokerReviewView.getBrokerReviews(accessToken, brokerId)
        bookmarkView.getBookmark(accessToken)

        Log.d("ReGetBookMark", "${updateUser.accessToken}/${updateUser.role}")
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

    override fun onAddBrokerReviewSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onAddBrokerReviewFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onBrokerReviewSuccess(brokerScore: BrokerScore?) {
        if (brokerScore != null) {
            binding.brokerRateIv.visibility = View.VISIBLE
            binding.intermediaryRateTv.text = brokerScore.score.toString()
        } else {
            binding.intermediaryRateTv.text = "등록된 평점 없음"
            binding.brokerRateIv.visibility = View.GONE
        }
    }

    override fun onBrokerReviewFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Refresh/Failure", "$code/$message")
                authService.refresh(user.accessToken, RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("Refresh/Failure", "$code/$message")
        }
    }
}