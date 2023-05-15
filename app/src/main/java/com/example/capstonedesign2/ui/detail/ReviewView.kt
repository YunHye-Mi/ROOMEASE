package com.example.capstonedesign2.ui.detail

interface ReviewView {
    fun onReviewSuccess(code: Int)
    fun onReviewFailure(code: Int, message: String)
}