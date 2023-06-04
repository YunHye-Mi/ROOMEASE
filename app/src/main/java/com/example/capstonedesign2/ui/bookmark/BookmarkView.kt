package com.example.capstonedesign2.ui.bookmark

import com.example.capstonedesign2.data.remote.EstateInfo

interface BookmarkView {
    fun onBookmarkSuccess(message: String)
    fun onBookmarkFailure(code: Int, message: String)

    fun onBMListSuccess(bookmarkList: ArrayList<EstateInfo>?)
    fun onBMListFailure(code: Int, message: String)
}