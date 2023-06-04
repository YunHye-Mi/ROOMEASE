package com.example.capstonedesign2.ui.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.ActivityBrokerReviewBinding
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.gson.Gson

class BrokerReviewActivity : AppCompatActivity(), BrokerReviewView, RefreshView {
    lateinit var binding: ActivityBrokerReviewBinding
    lateinit var user: User
    private val reviewView = ReviewService()
    private val authService = AuthService()
    private var gson = Gson()
    private var reliabilityRate = 0
    private var responseRate = 0
    private var kindnessRate = 0
    private var brokerId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBrokerReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewView.setBrokerReviewView(this)
        authService.setRefreshView(this)

        brokerId = intent.getIntExtra("brokerId", 0)
        Log.d("BrokerId", brokerId.toString())

        var spf = getSharedPreferences("currentUser", MODE_PRIVATE)
        var userJson = spf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)

        onClickListener()
    }

    private fun onClickListener() {
        binding.backIv.setOnClickListener {
            finish()
        }

        binding.reliabilityIv1.setOnClickListener {
            binding.reliabilityIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv2.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            reliabilityRate = 2

            writeAll()
        }

        binding.reliabilityIv2.setOnClickListener {
            binding.reliabilityIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            reliabilityRate = 4

            writeAll()
        }

        binding.reliabilityIv3.setOnClickListener {
            binding.reliabilityIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            reliabilityRate = 6

            writeAll()
        }

        binding.reliabilityIv4.setOnClickListener {
            binding.reliabilityIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            reliabilityRate = 8

            writeAll()
        }

        binding.reliabilityIv5.setOnClickListener {
            binding.reliabilityIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv5.setColorFilter(Color.parseColor("#FD7E36"))

            reliabilityRate = 10

            writeAll()
        }

        binding.responseIv1.setOnClickListener {
            binding.responseIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv2.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            responseRate = 2

            writeAll()
        }

        binding.responseIv2.setOnClickListener {
            binding.responseIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            responseRate = 4

            writeAll()
        }

        binding.responseIv3.setOnClickListener {
            binding.responseIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            responseRate = 6

            writeAll()
        }

        binding.responseIv4.setOnClickListener {
            binding.responseIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            responseRate = 8

            writeAll()
        }

        binding.responseIv5.setOnClickListener {
            binding.responseIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv5.setColorFilter(Color.parseColor("#FD7E36"))

            responseRate = 10

            writeAll()
        }

        binding.kindnessIv1.setOnClickListener {
            binding.kindnessIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv2.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            kindnessRate = 2

            writeAll()
        }

        binding.kindnessIv2.setOnClickListener {
            binding.kindnessIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            kindnessRate = 4

            writeAll()
        }

        binding.kindnessIv3.setOnClickListener {
            binding.kindnessIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            kindnessRate = 6

            writeAll()
        }

        binding.kindnessIv4.setOnClickListener {
            binding.kindnessIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            kindnessRate = 8

            writeAll()
        }

        binding.kindnessIv5.setOnClickListener {
            binding.kindnessIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv5.setColorFilter(Color.parseColor("#FD7E36"))

            kindnessRate = 10

            writeAll()

        }

    }

    private fun writeAll() {
        if (reliabilityRate > 0 && responseRate > 0 && kindnessRate > 0) {
            binding.doneTv.setTextColor(Color.parseColor("#754C24"))
            binding.doneTv.setBackgroundColor(Color.parseColor("#FDC536"))
            binding.doneTv.setOnClickListener {
                reviewView.addBrokerReview(user.accessToken, brokerId, BrokerReview(kindnessRate, responseRate, reliabilityRate))
            }
        } else {
            binding.doneTv.setTextColor(Color.parseColor("#666666"))
            binding.doneTv.setBackgroundColor(Color.parseColor("#C8C8C8"))

            binding.doneTv.setOnClickListener {
                Toast.makeText(this, "아직 작성되지 않은 부분이 있습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onAddBrokerReviewSuccess(message: String) {
        Toast.makeText(this, "리뷰 등록 완료😊", Toast.LENGTH_LONG).show()
        Log.d("BrokerReview/Success", message)
        finish()
    }

    override fun onAddBrokerReviewFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("GetReview/FAILURE", "$code/$message")
                authService.refresh(user.accessToken, RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("GetReview/FAILURE", "$code/$message")
        }
    }

    override fun onBrokerReviewSuccess(brokerScore: BrokerScore?) {
        TODO("Not yet implemented")
    }

    override fun onBrokerReviewFailure(code: Int, message: String) {
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

//        reviewView.addBrokerReview(user.accessToken, 브로커 아이디 추가, BrokerReview(kind_rate, response_rate, reliability_rate))

        Log.d("ReGetReview", "${updateUser.accessToken}/${updateUser.role}")
    }

    override fun onRefreshFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Refresh/Failure", "$code/$message")
                authService.refresh(user.refreshToken, RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("Refresh/Failure", "$code/$message")
        }
    }
}