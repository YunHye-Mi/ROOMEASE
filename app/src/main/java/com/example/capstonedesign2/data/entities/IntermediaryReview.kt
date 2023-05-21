package com.example.capstonedesign2.data.entities

import com.google.gson.annotations.SerializedName

data class IntermediaryReview(
    @SerializedName("userId") var userId : Int,
    @SerializedName("brokerId") var intermediaryId : Int,
    @SerializedName("rate") var rate : Double
)
