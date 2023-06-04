package com.example.capstonedesign2.ui.map

import com.example.capstonedesign2.data.remote.Document
import com.example.capstonedesign2.data.remote.ResultSearchKeyword


interface KaKaoView {
    fun onCategorySuccess(message: String, document: ArrayList<Document>)
    fun onCategoryFailure(message: String)

    fun onKeyWordSuccess(resultSearchKeyword: ResultSearchKeyword, message: String)
    fun onKeyWordFailure(message: String)
}