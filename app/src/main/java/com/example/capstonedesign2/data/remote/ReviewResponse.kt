package com.example.capstonedesign2.data.remote

import com.example.capstonedesign2.data.entities.Review
import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName(value = "status")val status: Int,
    @SerializedName(value = "success") val success: Boolean,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "data") val review: ReviewRequest
)

data class ReviewRequest(
    @SerializedName(value = "roomId") var roomId: Int,
    @SerializedName(value = "timeOfResidence") var timeOfResidence: String,
    @SerializedName(value = "ageGroup") var ageGroup: String,
    @SerializedName(value = "gender") var gender: String,
    @SerializedName(value = "transportationRating") var transportationRating: Int,
    @SerializedName(value = "neighborhoodRating") var neighborhoodRating: Int,
    @SerializedName(value = "livingConditionsRating") var livingConditionsRating: Int,
    @SerializedName(value = "freeComments") var freeComments: String?
)