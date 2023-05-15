package com.example.capstonedesign2.ui.detail

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.capstonedesign2.databinding.ActivityIntermediaryReviewBinding
import com.google.gson.Gson

class IntermediaryReviewActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntermediaryReviewBinding
    private var gson = Gson()
    private var reliability_rate = 0
    private var response_rate = 0
    private var kind_rate = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntermediaryReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickListener()
    }

    private fun onClickListener() {
        binding.backIv.setOnClickListener {
            onBackPressed()
        }

        binding.reliabilityIv1.setOnClickListener {
            binding.reliabilityIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv2.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            reliability_rate = 1
        }

        binding.reliabilityIv2.setOnClickListener {
            binding.reliabilityIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            reliability_rate = 2
        }

        binding.reliabilityIv3.setOnClickListener {
            binding.reliabilityIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.reliabilityIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            reliability_rate = 3
        }

        binding.reliabilityIv4.setOnClickListener {
            binding.reliabilityIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            reliability_rate = 4
        }

        binding.reliabilityIv1.setOnClickListener {
            binding.reliabilityIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.reliabilityIv5.setColorFilter(Color.parseColor("#FD7E36"))

            reliability_rate = 5
        }

        binding.responseIv1.setOnClickListener {
            binding.responseIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv2.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            response_rate = 1
        }

        binding.responseIv2.setOnClickListener {
            binding.responseIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            response_rate = 2
        }

        binding.responseIv3.setOnClickListener {
            binding.responseIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.responseIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            response_rate = 3
        }

        binding.responseIv4.setOnClickListener {
            binding.responseIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            response_rate = 4
        }

        binding.responseIv5.setOnClickListener {
            binding.responseIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.responseIv5.setColorFilter(Color.parseColor("#FD7E36"))

            response_rate = 5
        }

        binding.kindnessIv1.setOnClickListener {
            binding.kindnessIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv2.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            kind_rate = 1
        }

        binding.kindnessIv2.setOnClickListener {
            binding.kindnessIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv3.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            kind_rate = 2
        }

        binding.kindnessIv3.setOnClickListener {
            binding.kindnessIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv4.setColorFilter(Color.parseColor("#BBBBBB"))
            binding.kindnessIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            kind_rate = 3
        }

        binding.kindnessIv4.setOnClickListener {
            binding.kindnessIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv5.setColorFilter(Color.parseColor("#BBBBBB"))

            kind_rate = 4
        }

        binding.kindnessIv5.setOnClickListener {
            binding.kindnessIv1.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv2.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv3.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv4.setColorFilter(Color.parseColor("#FD7E36"))
            binding.kindnessIv5.setColorFilter(Color.parseColor("#FD7E36"))

            kind_rate = 5
        }

        if (reliability_rate != 0 && response_rate != 0 && kind_rate != 0) {
            binding.doneTv.setTextColor(Color.BLACK)
            binding.doneIv.setColorFilter(Color.BLACK)
            binding.doneLl.setOnClickListener {
                finish()
                Toast.makeText(this, "리뷰 등록 완료😊", Toast.LENGTH_LONG)
            }
        }

    }
}