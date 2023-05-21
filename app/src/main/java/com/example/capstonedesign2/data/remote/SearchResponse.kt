package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("full") var fullAddress: String,
    @SerializedName("dong") var dong: String
)

data class ResultRequest(
    @SerializedName("address") var address: String,
    @SerializedName("count") var count: Int,
    @SerializedName("sort") var sort: Int
)

data class ResultResponse(
    @SerializedName("id") var id: String, // 매물 번호
    @SerializedName("address")var address: String, // 주소
    @SerializedName("floor")var floor: String, // 층수
    @SerializedName("sales_type") var sales_type: String,
    @SerializedName("image")var coverImg: String?, // 이미지
    @SerializedName("rent")var rent: Float, // 월세 가격
    @SerializedName("size")var size: Float, // 평수
    @SerializedName("deposit") var deposit: Int // 보증금
)