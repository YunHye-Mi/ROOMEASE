package com.example.capstonedesign2.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.entities.Review
import com.example.capstonedesign2.databinding.FragmentReviewBinding

class ReviewFragment() : Fragment() {
    lateinit var binding: FragmentReviewBinding
    private val reviewData = arrayListOf<Review>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)

        binding.reviewTv.setOnClickListener {
            var intent = Intent(this.context, ReviewActivity::class.java)
            startActivity(intent)
        }

        initRV()

        return binding.root
    }

    private fun initRV() {
        var reviewRVAdapter = ReviewRVAdapter(reviewData)
        binding.reviewRv.adapter = reviewRVAdapter
        binding.reviewRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
    }
}