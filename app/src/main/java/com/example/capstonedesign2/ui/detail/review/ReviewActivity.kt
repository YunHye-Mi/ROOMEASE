package com.example.capstonedesign2.ui.detail.review

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.ActivityReviewBinding
import com.example.capstonedesign2.ui.detail.ReviewView
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.gson.Gson

class ReviewActivity : AppCompatActivity(), ReviewView, RefreshView {
    lateinit var binding: ActivityReviewBinding
    lateinit var accessToken: String
    lateinit var user: User
    private var gson = Gson()
    private var reviewView = ReviewService()
    private var authService = AuthService()
    private var period: String = ""
    private var age: String = ""
    private var gender: String = ""
    private var publicRate = 0
    private var ambientRate = 0
    private var livedRate = 0
    private var roomId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        roomId = intent.getIntExtra("room", 0)
        Log.d("RoomID", roomId.toString())

        var spf = getSharedPreferences("currentUser", MODE_PRIVATE)
        var userJson = spf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)
        accessToken = user.accessToken
        reviewView.setReviewView(this)
        authService.setRefreshView(this)

        onClickListener()
    }

    // edittext 이외 영역 클릭 시 키보드를 숨기도록 재정의
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                "android.webkit."
            )
        ) {
            var scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                hideKeyBoard()
            }
            binding.reviewEt.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    // 키보드 숨기기
    private fun hideKeyBoard(){
        var inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun onClickListener() {
        binding.backIv.setOnClickListener {
            finishAndRemoveTask()
        }

        binding.live5Tv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.round_shape)

            period = "5"

        }

        binding.live4Tv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.round_shape)

            period = "4"

        }

        binding.live3Tv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.round_shape)

            period = "3"

        }

        binding.live2Tv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.round_shape)

            period = "2"

        }

        binding.live1Tv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.round_shape)

            period = "1"

        }

        binding.liveCurrentTv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.gray_round_shape)

            period = "0"

        }

        binding.studentTv.setOnClickListener {
            binding.studentTv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.workerTv.setBackgroundResource(R.drawable.round_shape)
            binding.noTv.setBackgroundResource(R.drawable.round_shape)

            age = binding.studentTv.text.toString()

        }

        binding.workerTv.setOnClickListener {
            binding.studentTv.setBackgroundResource(R.drawable.round_shape)
            binding.workerTv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.noTv.setBackgroundResource(R.drawable.round_shape)

            age = binding.workerTv.text.toString()
        }

        binding.noTv.setOnClickListener {
            binding.studentTv.setBackgroundResource(R.drawable.round_shape)
            binding.workerTv.setBackgroundResource(R.drawable.round_shape)
            binding.noTv.setBackgroundResource(R.drawable.gray_round_shape)

            age = binding.noTv.text.toString()
        }

        binding.manTv.setOnClickListener {
            binding.manTv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.womanTv.setBackgroundResource(R.drawable.round_shape)

            gender = "male"
        }

        binding.womanTv.setOnClickListener {
            binding.manTv.setBackgroundResource(R.drawable.round_shape)
            binding.womanTv.setBackgroundResource(R.drawable.gray_round_shape)

            gender = "female"
        }

        binding.publicStar1Iv.setOnClickListener {
            binding.publicStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar2Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            publicRate = 2

            writeAll()
        }

        binding.publicStar2Iv.setOnClickListener {
            binding.publicStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            publicRate = 4

            writeAll()
        }

        binding.publicStar3Iv.setOnClickListener {
            binding.publicStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            publicRate = 6

            writeAll()
        }

        binding.publicStar4Iv.setOnClickListener {
            binding.publicStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            publicRate = 8

            writeAll()
        }

        binding.publicStar5Iv.setOnClickListener {
            binding.publicStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar5Iv.setColorFilter(Color.parseColor("#FD7E36"))

            publicRate = 10

            writeAll()
        }

        binding.ambientStar1Iv.setOnClickListener {
            binding.ambientStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar2Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            ambientRate = 2

            writeAll()
        }

        binding.ambientStar2Iv.setOnClickListener {
            binding.ambientStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            ambientRate = 4

            writeAll()
        }

        binding.ambientStar3Iv.setOnClickListener {
            binding.ambientStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            ambientRate = 6

            writeAll()
        }

        binding.ambientStar4Iv.setOnClickListener {
            binding.ambientStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            ambientRate = 8
        }

        binding.ambientStar5Iv.setOnClickListener {
            binding.ambientStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar5Iv.setColorFilter(Color.parseColor("#FD7E36"))

            ambientRate = 10
        }

        binding.liveStar1Iv.setOnClickListener {
            binding.liveStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar2Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            livedRate = 2

            writeAll()
        }

        binding.liveStar2Iv.setOnClickListener {
            binding.liveStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            livedRate = 4

            writeAll()
        }

        binding.liveStar3Iv.setOnClickListener {
            binding.liveStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            livedRate = 6

            writeAll()
        }

        binding.liveStar4Iv.setOnClickListener {
            binding.liveStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            livedRate = 8

            writeAll()
        }

        binding.liveStar5Iv.setOnClickListener {
            binding.liveStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar5Iv.setColorFilter(Color.parseColor("#FD7E36"))

            livedRate = 10

            writeAll()
        }
    }

    private fun writeAll() {
        if (period.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty() &&
            publicRate > 0 && ambientRate > 0 && livedRate > 0) {
            binding.doneTv.setTextColor(Color.parseColor("#754C24"))
            binding.doneTv.setBackgroundColor(Color.parseColor("#FDC536"))
            binding.doneTv.setOnClickListener {
                reviewView.addReview(
                    accessToken,
                    ReviewRequest(
                        roomId,
                        period,
                        age,
                        gender,
                        publicRate,
                        ambientRate,
                        livedRate,
                        binding.reviewEt.text.toString()
                    )
                )
            }
        } else {
            binding.doneTv.setTextColor(Color.parseColor("#666666"))
            binding.doneTv.setBackgroundColor(Color.parseColor("#C8C8C8"))

            binding.doneTv.setOnClickListener {
                Toast.makeText(this, "아직 작성되지 않은 부분이 있습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onAddReviewSuccess(message: String) {
        Toast.makeText(this, "리뷰 등록 완료😊", Toast.LENGTH_LONG).show()
        Log.d("SaveReview/SUCCESS", "리뷰 등록/$message")
        finish()
    }

    override fun onAddReviewFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("GetReview/FAILURE", "$code/$message")
                authService.refresh(RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("GetReview/FAILURE", "$code/$message")
        }
    }

    override fun onReviewSuccess(response: ArrayList<Review>) {
        TODO("Not yet implemented")
    }

    override fun onReviewFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteReviewSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteReviewFailure(code: Int, message: String) {
        TODO("Not yet implemented")
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

        editor.commit()

        reviewView.addReview(
            accessToken,
            ReviewRequest(
                roomId,
                period,
                age,
                gender,
                publicRate,
                ambientRate,
                livedRate,
                binding.reviewEt.text.toString()
            )
        )

        Log.d("ReAddReview", "${updateUser.accessToken}/$roomId")
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
}