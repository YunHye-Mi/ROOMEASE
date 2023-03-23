package com.example.capstonedesign2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstonedesign2.databinding.ActivityDetailBinding
import com.example.capstonedesign2.databinding.ActivityMapBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}