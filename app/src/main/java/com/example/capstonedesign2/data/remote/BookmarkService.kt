package com.example.capstonedesign2.data.remote

import com.example.capstonedesign2.ui.bookmark.BookmarkView
import com.example.capstonedesign2.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class BookmarkService() {
    private lateinit var bookmarkView: BookmarkView

    fun setBookmarkView(bookmarkView: BookmarkView) {
        this.bookmarkView = bookmarkView
    }

    fun addBookmark(authorization: String, roomId: Int) {
        val bookmarkService = getRetrofit().create(BookmarkRetrofitInterface::class.java)
        bookmarkService.addBookmark("Barer $authorization", roomId).enqueue(object : Callback<BookmarkResponse> {
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                if (response.isSuccessful) {
                    var resp: BookmarkResponse? = response.body()
                    if (resp != null) {
                        bookmarkView.onBookmarkSuccess(resp.status, resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                bookmarkView.onBookmarkFailure(t.message.toString())
            }

        })
    }

    fun getBookmark(authorization: String) {
        val bookmarkService = getRetrofit().create(BookmarkRetrofitInterface::class.java)
        bookmarkService.getBookmark("Barer $authorization").enqueue(object : Callback<BookmarkListResponse> {
            override fun onResponse(call: Call<BookmarkListResponse>, response: Response<BookmarkListResponse>) {
                if (response.isSuccessful) {
                    var resp: BookmarkListResponse? = response.body()
                    if (resp != null) {
                        bookmarkView.onBMListSuccess(resp)
                    }
                }            }

            override fun onFailure(call: Call<BookmarkListResponse>, t: Throwable) {
                bookmarkView.onBMListFailure(t.message.toString())
            }
        })
    }
}