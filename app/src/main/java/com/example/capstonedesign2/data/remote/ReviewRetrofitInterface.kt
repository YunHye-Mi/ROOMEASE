package com.example.capstonedesign2.data.remote

import com.example.capstonedesign2.data.entities.User
import retrofit2.Call
import retrofit2.http.*

interface ReviewRetrofitInterface {

    @POST("/api/review")
    fun addReview(@Header(value = "Authorization") authorization: String, @Body reviewRequest: ReviewRequest): Call<ReviewResponse>

    @GET("/api/review/list")
    fun getReviews(@Header(value = "Authorization") authorization: String, @Query(value = "room_id") roomId: Int): Call<ReviewResponse>

    @DELETE("/api/review/delete")
    fun deleteReview(@Header(value = "Authorization") authorization: String, @Query(value = "review_id") reviewId: Int): Call<ReviewResponse>

    @POST("/api/review/broker")
    fun addBrokerReview(@Header(value = "Authorization") authorization: String, @Query("broker_id") brokerId: Int, @Body brokerReview: BrokerReview): Call<BrokerReviewResponse>

    @GET("/api/review/broker")
    fun getBrokerReviews(@Header(value = "Authorization") authorization: String, @Query(value = "broker_id") brokerId: Int): Call<BrokerReviewResponse>
}