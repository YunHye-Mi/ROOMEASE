package com.example.capstonedesign2.ui.map.search

import com.example.capstonedesign2.data.remote.ResultResponse

interface ResultView {
    fun onResultSuccess(response: ArrayList<ResultResponse>)
    fun onResultFailure(message: String)
}