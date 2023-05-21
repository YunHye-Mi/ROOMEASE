package com.example.capstonedesign2.ui.map

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import com.example.capstonedesign2.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {
    lateinit var binding : ActivityFilterBinding
    private var rent = ""
    private var area_min = 0
    private var area_max = 0
    private var price_min_d = 0
    private var price_max_d = 0
    private var price_min_m = 0
    private var price_max_m = 0
    private var price_min_a = 0
    private var price_max_a = 0
    private var floor = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickListener()

    }

    fun onClickListener() {

        binding.closeIv.setOnClickListener {
            onBackPressed()
        }

        binding.rentLl[0].setOnClickListener {
            binding.charterTv.setTextColor(Color.parseColor("#754C24"))
            binding.monthlyTv.setTextColor(Color.BLACK)

            rent = binding.charterTv.text.toString()

            binding.priceMonthlyTv.visibility = View.GONE
            binding.monthlyLl.visibility = View.GONE
            binding.adminTv.visibility = View.GONE
            binding.adminLl.visibility = View.GONE

            binding.depositTv.text = "전세"
        }

        binding.rentLl[1].setOnClickListener {
            binding.monthlyTv.setTextColor(Color.parseColor("#754C24"))
            binding.charterTv.setTextColor(Color.BLACK)

            binding.depositTv.text = "보증금"
            binding.priceMonthlyTv.visibility = View.VISIBLE
            binding.monthlyLl.visibility = View.VISIBLE
            binding.adminTv.visibility = View.VISIBLE
            binding.adminLl.visibility = View.VISIBLE

            rent = binding.monthlyTv.text.toString()
        }

        binding.floorLl[0].setOnClickListener {
            binding.bottomTv.setTextColor(Color.parseColor("#754C24"))
            binding.groundTv.setTextColor(Color.BLACK)
//            binding.topTv.setTextColor(Color.BLACK)

            floor = binding.bottomTv.text.toString()
        }
        binding.floorLl[1].setOnClickListener {
            binding.bottomTv.setTextColor(Color.BLACK)
            binding.groundTv.setTextColor(Color.parseColor("#754C24"))
//            binding.topTv.setTextColor(Color.BLACK)

            floor = binding.groundTv.text.toString()

        }
//        binding.floorLl[2].setOnClickListener {
//            binding.bottomTv.setTextColor(Color.BLACK)
//            binding.groundTv.setTextColor(Color.BLACK)
//            binding.topTv.setTextColor(Color.parseColor("#754C24"))
//
//            floor = binding.topTv.text.toString()
//        }

        val sharedPreferences = getSharedPreferences("filter", MODE_PRIVATE)
        binding.applyTv.setOnClickListener {
            var editor = with(sharedPreferences.edit()) {
                putString("rent", rent)
                if (binding.minEt.text.isNotEmpty() && binding.maxEt.text.isNotEmpty()) {
                    putInt("area_min", binding.minEt.text.toString().toInt())
                    putInt("area_max", binding.maxEt.text.toString().toInt())
                }

                if (binding.minDepositEt.text.isNotEmpty() && binding.maxDepositEt.text.isNotEmpty()) {
                    putInt("price_min_d", binding.minDepositEt.text.toString().toInt())
                    putInt("price_max_d", binding.maxDepositEt.text.toString().toInt())
                }

                if (binding.minMonthlyEt.text.isNotEmpty() && binding.maxMonthlyEt.text.isNotEmpty()) {
                    putInt("price_min_m", binding.minMonthlyEt.text.toString().toInt())
                    putInt("price_max_m", binding.maxMonthlyEt.text.toString().toInt())
                }

                if (binding.minAdminEt.text.isNotEmpty() && binding.maxMonthlyEt.text.isNotEmpty()) {
                    putInt("price_min_a", binding.minAdminEt.text.toString().toInt())
                    putInt("price_max_a", binding.maxAdminEt.text.toString().toInt())
                }
                putString("floor", floor)
            }

            editor.commit()

            finish()
        }

        binding.clearTv.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.commit()

            binding.depositTv.text = "전세/보증금"
            binding.priceMonthlyTv.visibility = View.VISIBLE
            binding.monthlyLl.visibility = View.VISIBLE
            binding.adminTv.visibility = View.VISIBLE
            binding.adminLl.visibility = View.VISIBLE
            binding.monthlyTv.setTextColor(Color.BLACK)
            binding.charterTv.setTextColor(Color.BLACK)
            binding.bottomTv.setTextColor(Color.BLACK)
            binding.groundTv.setTextColor(Color.BLACK)
//            binding.topTv.setTextColor(Color.BLACK)
            binding.minEt.text.clear()
            binding.maxEt.text.clear()
            binding.minDepositEt.text.clear()
            binding.maxDepositEt.text.clear()
            binding.minMonthlyEt.text.clear()
            binding.maxMonthlyEt.text.clear()
            binding.minAdminEt.text.clear()
            binding.maxAdminEt.text.clear()
        }
    }
}
