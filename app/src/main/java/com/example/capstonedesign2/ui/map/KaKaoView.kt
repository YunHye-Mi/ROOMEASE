package com.example.capstonedesign2.ui.map

import com.example.capstonedesign2.data.remote.Place
import com.example.capstonedesign2.data.remote.ResultSearchKeyword


interface KaKaoView {
    fun onCategorySuccess(place: Place)
    fun onCategoryFailure(code: Int, message: String)

    fun onKeyWordSuccess(resultSearchKeyword: ResultSearchKeyword)
    fun onKeyWordFailure(code: Int, message: String)
}