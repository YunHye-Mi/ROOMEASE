package com.example.capstonedesign2.ui.detail.review

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.data.entities.Review
import com.example.capstonedesign2.databinding.ActivityReviewBinding
import com.google.gson.Gson

class ReviewActivity : AppCompatActivity() {
    lateinit var binding: ActivityReviewBinding
    private var gson = Gson()
    var period = ""
    var age = ""
    var gender = ""
    var public_rate = 0
    var ambient_rate = 0
    var lived_rate = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            onBackPressed()
        }

        binding.live5Tv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.round_shape)

            period = binding.live5Tv.text.toString()
        }

        binding.live4Tv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.round_shape)

            period = binding.live4Tv.text.toString()
        }

        binding.live3Tv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.round_shape)

            period = binding.live3Tv.text.toString()
        }

        binding.live2Tv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.round_shape)

            period = binding.live2Tv.text.toString()
        }

        binding.live1Tv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.gray_round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.round_shape)

            period = binding.live1Tv.text.toString()
        }

        binding.liveCurrentTv.setOnClickListener {
            binding.live5Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live4Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live3Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live2Tv.setBackgroundResource(R.drawable.round_shape)
            binding.live1Tv.setBackgroundResource(R.drawable.round_shape)
            binding.liveCurrentTv.setBackgroundResource(R.drawable.gray_round_shape)

            period = binding.liveCurrentTv.text.toString()
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

            gender = binding.manTv.text.toString()
        }

        binding.womanTv.setOnClickListener {
            binding.manTv.setBackgroundResource(R.drawable.round_shape)
            binding.womanTv.setBackgroundResource(R.drawable.gray_round_shape)

            gender = binding.womanTv.text.toString()
        }

        binding.publicStar1Iv.setOnClickListener {
            binding.publicStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar2Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            public_rate = 1
        }

        binding.publicStar2Iv.setOnClickListener {
            binding.publicStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            public_rate = 2
        }

        binding.publicStar3Iv.setOnClickListener {
            binding.publicStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.publicStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            public_rate = 3
        }

        binding.publicStar4Iv.setOnClickListener {
            binding.publicStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            public_rate = 4
        }

        binding.publicStar5Iv.setOnClickListener {
            binding.publicStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.publicStar5Iv.setColorFilter(Color.parseColor("#FD7E36"))

            public_rate = 5
        }

        binding.ambientStar1Iv.setOnClickListener {
            binding.ambientStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar2Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            ambient_rate = 1
        }

        binding.ambientStar2Iv.setOnClickListener {
            binding.ambientStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            ambient_rate = 2
        }

        binding.ambientStar3Iv.setOnClickListener {
            binding.ambientStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.ambientStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            ambient_rate = 3
        }

        binding.ambientStar4Iv.setOnClickListener {
            binding.ambientStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            ambient_rate = 4
        }

        binding.ambientStar5Iv.setOnClickListener {
            binding.ambientStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.ambientStar5Iv.setColorFilter(Color.parseColor("#FD7E36"))

            ambient_rate = 5
        }

        binding.liveStar1Iv.setOnClickListener {
            binding.liveStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar2Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            lived_rate = 1
        }

        binding.liveStar2Iv.setOnClickListener {
            binding.liveStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar3Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            lived_rate = 2
        }

        binding.liveStar3Iv.setOnClickListener {
            binding.liveStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar4Iv.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.liveStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            lived_rate = 3
        }

        binding.liveStar4Iv.setOnClickListener {
            binding.liveStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar5Iv.setColorFilter(Color.parseColor("#BBBBBB"))

            lived_rate = 4
        }

        binding.liveStar5Iv.setOnClickListener {
            binding.liveStar1Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar2Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar3Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar4Iv.setColorFilter(Color.parseColor("#FD7E36"))
            binding.liveStar5Iv.setColorFilter(Color.parseColor("#FD7E36"))

            lived_rate = 5
        }

        if (period.isNotEmpty() && age.isNotEmpty() && gender.isNotEmpty()) {
            binding.nextTv.setTextColor(Color.BLACK)
            binding.nextIv.setColorFilter(Color.BLACK)
            binding.nextLl.setOnClickListener {
                binding.frontPage.visibility = View.GONE
                binding.secondPage.visibility = View.VISIBLE
            }
        }

        if (public_rate != 0 && ambient_rate != 0 && lived_rate != 0 && binding.reviewEt.text.isNotEmpty()) {
            binding.doneTv.setTextColor(Color.BLACK)
            binding.doneIv.setColorFilter(Color.BLACK)
            binding.doneLl.setOnClickListener {
//                saveReview()
                finish()
                Toast.makeText(this, "리뷰 등록 완료😊", Toast.LENGTH_LONG)
            }
        }

    }

//    private fun saveReview(): Review {
//        val estate = intent.getStringExtra("estate")
//        val estateJson = gson.fromJson(estate, Estate::class.java)
//        return Review("mm", "0", System.currentTimeMillis(), period, age, gender, public_rate, ambient_rate, lived_rate, binding.reviewEt.text.toString())
//    }
}