package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName(value = "status")val status: Int,
    @SerializedName(value = "success") val success: Boolean,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "data") val data: ArrayList<Review>?
)

data class Review(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("roomId") val roomId: Int,
    @SerializedName("freeComments") val freeComments: String,
    @SerializedName("timeOfResidence") val period: Int,
    @SerializedName("score") val score: Double,
    @SerializedName("myReview") val myReview: Boolean
)


data class ReviewRequest(
    @SerializedName(value = "roomId") var roomId: Int,
    @SerializedName(value = "timeOfResidence") var timeOfResidence: String,
    @SerializedName(value = "ageGroup") var ageGroup: String,
    @SerializedName(value = "gender") var gender: String,
    @SerializedName(value = "transportationRating") var transportationRating: Int,
    @SerializedName(value = "neighborhoodRating") var neighborhoodRating: Int,
    @SerializedName(value = "livingConditionsRating") var livingConditionsRating: Int,
    @SerializedName(value = "freeComments") var freeComments: String
)

data class BrokerReview(
    @SerializedName(value = "kindness") val kindness: Int,
    @SerializedName(value = "responseTime") val response: Int,
    @SerializedName(value = "reliability") val reliability: Int
)

data class BrokerReviewResponse(
    @SerializedName(value = "status")val status: Int,
    @SerializedName(value = "success") val success: Boolean,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "data") val data: BrokerScore?
)

data class BrokerScore(
    @SerializedName("score") val score: Double
)