package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName

data class BookmarkResponse(
    @SerializedName(value = "status")val status: Int,
    @SerializedName(value = "success") val success: Boolean,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "data") val bookmark: ArrayList<EstateInfo>?
)