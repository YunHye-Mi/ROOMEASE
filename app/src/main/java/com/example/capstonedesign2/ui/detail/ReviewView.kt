package com.example.capstonedesign2.ui.detail

import com.example.capstonedesign2.data.remote.Review

interface ReviewView {

    fun onAddReviewSuccess(message: String)
    fun onAddReviewFailure(code: Int, message: String)

    fun onReviewSuccess(response: ArrayList<Review>)
    fun onReviewFailure(code: Int, message: String)

    fun onDeleteReviewSuccess(message: String)
    fun onDeleteReviewFailure(code: Int, message: String)
}