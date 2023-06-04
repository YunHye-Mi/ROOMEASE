package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.ui.addEstate.BrokerView
import com.example.capstonedesign2.ui.bookmark.BookmarkView
import com.example.capstonedesign2.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkService() {
    private lateinit var bookmarkView: BookmarkView
    private lateinit var brokerView: BrokerView

    fun setBookmarkView(bookmarkView: BookmarkView) {
        this.bookmarkView = bookmarkView
    }

    fun setBrokerView(brokerView: BrokerView) {
        this.brokerView = brokerView
    }

    fun addBookmark(authorization: String, roomId: Int) {
        val bookmarkService = getRetrofit().create(BookmarkRetrofitInterface::class.java)
        bookmarkService.addBookmark("Bearer $authorization", roomId).enqueue(object : Callback<BookmarkResponse> {
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                if (response.isSuccessful) {
                    var resp: BookmarkResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            bookmarkView.onBookmarkSuccess(resp.message)
                        } else {
                            when (resp.status) {
                                401 -> bookmarkView.onBookmarkFailure(resp.status, resp.message)
                                403 -> bookmarkView.onBookmarkFailure(resp.status, resp.message)
                            }
                        }
                    }
                    Log.d("BOOKMARK/LINK", "북마크 API 연결")
                }
            }

            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                Log.d("BookMark/Failure", t.message.toString())
            }

        })
    }

    fun getBookmark(authorization: String) {
        val bookmarkService = getRetrofit().create(BookmarkRetrofitInterface::class.java)
        bookmarkService.getBookmark("Bearer $authorization").enqueue(object : Callback<BookmarkResponse> {
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                if (response.isSuccessful) {
                    var resp: BookmarkResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            bookmarkView.onBMListSuccess(resp.bookmark)
                        } else {
                            when (resp.status) {
                                401 -> bookmarkView.onBMListFailure(resp.status, resp.message)
                                403 -> bookmarkView.onBMListFailure(resp.status, resp.message)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                Log.d("BookMark/Failure", t.message.toString())
            }
        })
    }

    fun getBrokerEstates(authorization: String) {
        val bookmarkService = getRetrofit().create(BookmarkRetrofitInterface::class.java)
        bookmarkService.getBrokerEstates("Bearer $authorization").enqueue(object : Callback<BookmarkResponse> {
            override fun onResponse(call: Call<BookmarkResponse>, response: Response<BookmarkResponse>) {
                if (response.isSuccessful) {
                    var resp: BookmarkResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            brokerView.onGetRoomSuccess(resp.bookmark)
                        } else {
                            when (resp.status) {
                                401 -> brokerView.onGetRoomFailure(resp.status, resp.message)
                                403 -> brokerView.onGetRoomFailure(resp.status, resp.message)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BookmarkResponse>, t: Throwable) {
                Log.d("Broker/Failure", t.message.toString())
            }

        })
    }
}