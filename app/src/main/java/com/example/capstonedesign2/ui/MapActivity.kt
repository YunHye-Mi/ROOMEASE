package com.example.capstonedesign2.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginStart
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    lateinit var binding : ActivityMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickListener()
    }

    private fun onClickListener() {
        binding.backIv.setOnClickListener {
            onBackPressed()
        }

        binding.rentTypeBt.setOnClickListener {
            binding.llAfterSelectFilter.visibility = View.VISIBLE
            binding.mapFilterLl.visibility = View.GONE
            binding.selectAfterTv.text = binding.rentTypeBt.text

            val tv1 = TextView(this)
            val tv2 = TextView(this)

            tv1.text = "전세"
            tv1.setTextAppearance(R.style.map_select_filter_text)

            tv2.text = "월세"
            tv1.setTextAppearance(R.style.map_select_filter_text)

            val layoutParams = tv1.layoutParams as LinearLayout.LayoutParams
            layoutParams.setMargins(10, 0, 0, 0)

            binding.selectAfterGl.columnCount = 2
            binding.selectAfterGl.rowCount = 1
            binding.selectAfterGl.addView(tv1, 0)
            binding.selectAfterGl.addView(tv2, 1)
        }

        binding.buildingTypeBt.setOnClickListener {
            binding.llAfterSelectFilter.visibility = View.VISIBLE
            binding.mapFilterLl.visibility = View.GONE
            binding.selectAfterTv.text = binding.rentTypeBt.text
        }

        binding.areaBt.setOnClickListener {
            binding.llAfterSelectFilter.visibility = View.VISIBLE
            binding.mapFilterLl.visibility = View.GONE
            binding.selectAfterTv.text = binding.rentTypeBt.text
        }

        binding.priceBt.setOnClickListener {
            binding.llAfterSelectFilter.visibility = View.VISIBLE
            binding.mapFilterLl.visibility = View.GONE
            binding.selectAfterTv.text = binding.rentTypeBt.text
        }

        binding.floorBt.setOnClickListener {
            binding.llAfterSelectFilter.visibility = View.VISIBLE
            binding.mapFilterLl.visibility = View.GONE
            binding.selectAfterTv.text = binding.rentTypeBt.text
        }
    }
}