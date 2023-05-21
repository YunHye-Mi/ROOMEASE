package com.example.capstonedesign2.ui.bookmark

import com.example.capstonedesign2.data.remote.BookmarkListResponse

interface BookmarkView {
    fun onBookmarkSuccess(code: Int, message: String)
    fun onBookmarkFailure(message: String)

    fun onBMListSuccess(bookmarkListResponse: BookmarkListResponse)
    fun onBMListFailure(message: String)
}