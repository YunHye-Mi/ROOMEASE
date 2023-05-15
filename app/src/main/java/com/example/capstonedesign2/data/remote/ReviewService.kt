package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.ui.detail.ReviewView
import com.example.capstonedesign2.utils.getRetrofit
import retrofit2.Call
import retrofit2.Response

class ReviewService() {
    private lateinit var reviewView: ReviewView

    fun setReviewView(reviewView: ReviewView) {
        this.reviewView = reviewView
    }

    fun addReview(authorization: String, reviewRequest: ReviewRequest) {
        var reviewService = getRetrofit().create(ReviewRetrofitInterface::class.java)
        reviewService.addReview(authorization, reviewRequest).enqueue(object : retrofit2.Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                Log.d("Login/SUCCESS", response.message().toString())
                val resp: ReviewResponse = response.body()!!
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {

            }
        })
    }

    fun getReviews(authorization: String, roomId: Int) {
        var reviewService = getRetrofit().create(ReviewRetrofitInterface::class.java)
        reviewService.getReviews(authorization, roomId).enqueue(object : retrofit2.Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {

            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {

            }

        })
    }

    fun deleteReview(authorization: String, reviewId: Int) {
        var reviewService = getRetrofit().create(ReviewRetrofitInterface::class.java)
        reviewService.deleteReview(authorization, reviewId).enqueue(object : retrofit2.Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {

            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {

            }

        })
    }
}