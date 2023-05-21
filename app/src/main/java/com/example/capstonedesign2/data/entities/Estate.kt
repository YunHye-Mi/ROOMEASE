package com.example.capstonedesign2.data.entities

import com.google.gson.annotations.SerializedName

data class Estate(
    @SerializedName("id") var id: String, // 매물 번호
    @SerializedName("deposit") var deposit: Int, // 보증금
    @SerializedName("address")var address: String, // 주소
    @SerializedName("floor")var floor: String, // 층수
    @SerializedName("image")var coverImg: String?, // 이미지
    @SerializedName("rent")var rent: Float, // 월세 가격
    @SerializedName("manage_cost")var manage_cost: Float, // 관리비
    @SerializedName("service_type")var service_type: String, //
    @SerializedName("sales_type")var sales_type: String, //전월세 유형
    @SerializedName("size")var size: Float, // 평수
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double
)