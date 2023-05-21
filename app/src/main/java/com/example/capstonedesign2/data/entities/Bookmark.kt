package com.example.capstonedesign2.data.entities

import com.google.gson.annotations.SerializedName

data class Bookmark(
    @SerializedName("id") var id: Int,
    @SerializedName("userId") var userId : Int,
    @SerializedName("roomId") var roomId : Int
)