package com.example.capstonedesign2.ui.map.search

import com.example.capstonedesign2.data.remote.SearchResponse

interface SearchTextView {
    fun onSearchSuccess(response: SearchResponse)
    fun onSearchFailure(toString: String)
}