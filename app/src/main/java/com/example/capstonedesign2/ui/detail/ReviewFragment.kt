package com.example.capstonedesign2.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.data.entities.Review
import com.example.capstonedesign2.databinding.FragmentReviewBinding
import com.example.capstonedesign2.ui.detail.review.ReviewActivity
import com.example.capstonedesign2.ui.detail.review.ReviewRVAdapter
import com.google.gson.Gson

class ReviewFragment() : Fragment() {
    lateinit var binding: FragmentReviewBinding
    private val reviewData = arrayListOf<Review>()
    private var gson = Gson()
    lateinit var estate: Estate
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)

        binding.reviewTv.setOnClickListener {
            var intent = Intent(this.context, ReviewActivity::class.java)
            val estateJson = gson.toJson(estate)
            intent.putExtra("estate", estateJson)
            startActivity(intent)
        }

        reviewData.apply {
            add(Review("mm", 1,20230304, "4년 이내 거주", "대학생", "여성", 4, 3, 3, "집에서 곰팡이 냄새가 너무 나요."))
        }

        var reviewRVAdapter = ReviewRVAdapter(reviewData)
        binding.reviewRv.adapter = reviewRVAdapter
        binding.reviewRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    companion object {
        fun newInstance(estateJson: String?): ReviewFragment {
            var fragment = ReviewFragment()
            fragment.estate = fragment.gson.fromJson(estateJson, Estate::class.java)
            return fragment
        }
    }
}