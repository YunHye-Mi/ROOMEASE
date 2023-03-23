package com.example.capstonedesign2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstonedesign2.databinding.ActivityMapBinding
import com.example.capstonedesign2.databinding.ActivityResultBinding
import com.example.capstonedesign2.databinding.ActivitySearchBinding

class ResultActivity : AppCompatActivity() {
    lateinit var binding : ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}