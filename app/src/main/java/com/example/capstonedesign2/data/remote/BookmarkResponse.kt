package com.example.capstonedesign2.data.remote

import com.example.capstonedesign2.data.entities.Bookmark
import com.example.capstonedesign2.data.entities.Review
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class BookmarkResponse(
    @SerializedName(value = "status")val status: Int,
    @SerializedName(value = "success") val success: Boolean,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "data") val bookmark: Bookmark
)

data class BookmarkListResponse(
    @SerializedName(value = "status")val status: Int,
    @SerializedName(value = "success") val success: Boolean,
    @SerializedName(value = "message") val message: String,
    @SerializedName(value = "data") val bookmark: Bookmark,
    @SerializedName(value = "createdAt") val createdAt: LocalDateTime,
    @SerializedName(value = "updatedAt") val updatedAt: LocalDateTime
)