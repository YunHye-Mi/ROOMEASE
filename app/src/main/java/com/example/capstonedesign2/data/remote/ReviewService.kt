package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.ui.detail.BrokerReviewView
import com.example.capstonedesign2.ui.detail.ReviewView
import com.example.capstonedesign2.utils.getRetrofit
import retrofit2.Call
import retrofit2.Response

class ReviewService() {
    private lateinit var reviewView: ReviewView
    private lateinit var brokerReviewView: BrokerReviewView

    fun setReviewView(reviewView: ReviewView) {
        this.reviewView = reviewView
    }

    fun setBrokerReviewView(brokerReviewView: BrokerReviewView) {
        this.brokerReviewView = brokerReviewView
    }

    fun addReview(authorization: String, reviewRequest: ReviewRequest) {
        var reviewService = getRetrofit().create(ReviewRetrofitInterface::class.java)
        reviewService.addReview("Bearer $authorization", reviewRequest).enqueue(object : retrofit2.Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    val resp: ReviewResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            reviewView.onAddReviewSuccess(resp.message)
                        } else {
                            when (resp.status) {
                                401 -> reviewView.onAddReviewFailure(resp.status, resp.message)
                                403 -> reviewView.onAddReviewFailure(resp.status, resp.message)
                            }
                        }
                    }
                    Log.d("ADDREVIEW/SUCCESS", "addReview api 연결 성공")
                }
            }
            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Log.d("Review/Failure", t.message.toString())
            }
        })
    }

    fun getReviews(authorization: String, roomId: Int) {
        var reviewService = getRetrofit().create(ReviewRetrofitInterface::class.java)
        reviewService.getReviews("Bearer $authorization", roomId).enqueue(object : retrofit2.Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    var resp: ReviewResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            reviewView.onReviewSuccess(resp.data!!)
                        } else {
                            when (resp.status) {
                                401 -> reviewView.onReviewFailure(resp.status, resp.message)
                                403 -> reviewView.onReviewFailure(resp.status, resp.message)
                            }
                        }
                    }
                    Log.d("GETREVIEW/SUCCESS", response.message().toString())
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Log.d("Review/Failure", t.message.toString())
            }

        })
    }

    fun deleteReview(authorization: String, reviewId: Int) {
        var reviewService = getRetrofit().create(ReviewRetrofitInterface::class.java)
        reviewService.deleteReview("Bearer $authorization", reviewId).enqueue(object : retrofit2.Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    val resp: ReviewResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            reviewView.onDeleteReviewSuccess(resp.message)
                        } else {
                            when (resp.status) {
                                401 -> reviewView.onDeleteReviewFailure(resp.status, resp.message)
                                403 -> reviewView.onDeleteReviewFailure(resp.status, resp.message)
                            }
                        }
                    }
                    Log.d("LinkReviewApi/Success", "리뷰 삭제 Api 연결")
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Log.d("Review/Failure", t.message.toString())
            }
        })
    }

    fun addBrokerReview(authorization: String, brokerId: Int, brokerReview: BrokerReview) {
        var reviewService = getRetrofit().create(ReviewRetrofitInterface::class.java)
        reviewService.addBrokerReview("Bearer $authorization", brokerId, brokerReview).enqueue(object : retrofit2.Callback<BrokerReviewResponse> {
            override fun onResponse(call: Call<BrokerReviewResponse>, response: Response<BrokerReviewResponse>) {
                if (response.isSuccessful) {
                    val resp: BrokerReviewResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            brokerReviewView.onAddBrokerReviewSuccess(resp.message)
                        } else {
                            when (resp.status) {
                                401 -> brokerReviewView.onAddBrokerReviewFailure(resp.status, resp.message)
                                403 -> brokerReviewView.onAddBrokerReviewFailure(resp.status, resp.message)
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<BrokerReviewResponse>, t: Throwable) {
                Log.d("Review/Failure", t.message.toString())
            }
        })
    }

    fun getBrokerReviews(authorization: String, brokerId: Int) {
        var reviewService = getRetrofit().create(ReviewRetrofitInterface::class.java)
        reviewService.getBrokerReviews("Bearer $authorization", brokerId).enqueue(object : retrofit2.Callback<BrokerReviewResponse> {
            override fun onResponse(call: Call<BrokerReviewResponse>, response: Response<BrokerReviewResponse>) {
                if (response.isSuccessful) {
                    var resp: BrokerReviewResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            brokerReviewView.onBrokerReviewSuccess(resp.data)
                        } else {
                            when (resp.status) {
                                401 -> brokerReviewView.onBrokerReviewFailure(resp.status, resp.message)
                                403 -> brokerReviewView.onBrokerReviewFailure(resp.status, resp.message)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BrokerReviewResponse>, t: Throwable) {
                Log.d("BrokerReview/Failure", t.message.toString())
            }
        })
    }
}