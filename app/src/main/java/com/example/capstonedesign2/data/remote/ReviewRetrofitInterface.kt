package com.example.capstonedesign2.data.remote

import com.example.capstonedesign2.data.entities.User
import retrofit2.Call
import retrofit2.http.*

interface ReviewRetrofitInterface {

    @POST("/api/review")
    fun addReview(@Header(value = "Authorization") authorization: String, @Body reviewRequest: ReviewRequest): Call<ReviewResponse>

    @GET("/api/review/list?room_id={room_id}")
    fun getReviews(@Header(value = "Authorization") authorization: String, @Path(value = "room_id") roomId: Int): Call<ReviewResponse>

    @DELETE("/api/review/delete?review_id={review_id}")
    fun deleteReview(@Header(value = "Authorization") authorization: String,@Path(value = "review_id") reviewId: Int): Call<ReviewResponse>

}