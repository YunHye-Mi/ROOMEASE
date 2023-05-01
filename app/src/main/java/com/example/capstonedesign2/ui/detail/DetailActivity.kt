package com.example.capstonedesign2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstonedesign2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding
    private val information = arrayListOf("대중교통", "주변환경", "거주환경", "리뷰")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailAdapter = DetailVPAdapter(this)
        binding.detailVp.adapter = detailAdapter
        TabLayoutMediator(binding.detailTb, binding.detailVp){
            tab, position ->
            tab.text = information[position]
        }.attach()
    }
}