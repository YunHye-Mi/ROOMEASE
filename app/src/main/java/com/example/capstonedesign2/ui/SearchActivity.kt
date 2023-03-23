package com.example.capstonedesign2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstonedesign2.databinding.ActivityMapBinding
import com.example.capstonedesign2.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding : ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}