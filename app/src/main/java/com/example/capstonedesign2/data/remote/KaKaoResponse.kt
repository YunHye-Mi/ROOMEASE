package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName

data class ResultSearchKeyword(
    @SerializedName("meta") val meta: Meta, // 장소 메타 데이터
    @SerializedName("documents") val documents: List<Document> // 검색 결과
)

data class Meta(
    @SerializedName("is_end") val is_end: Boolean,
    @SerializedName("pageable_count") val pageable_count: Int,
    @SerializedName("same_name") val same_name: RegionInfo?,
    @SerializedName("total_count") val total_count: Int
)

data class RegionInfo(
    @SerializedName("region") val region: ArrayList<String>,
    @SerializedName("keyword") val keyword: String,
    @SerializedName("selected_region") val selected_region: String
)

data class Document(
    @SerializedName("id") val id: String,
    @SerializedName("place_name") val placeName: String,
    @SerializedName("category_group_code") val category_group_code: String,
    @SerializedName("category_group_name") val category_group_name: String,
    @SerializedName("category_name") val category_name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address_name") val addressName: String,
    @SerializedName("road_address_name") val road_address_name: String,
    @SerializedName("x") val x: String, // x가 경도
    @SerializedName("y") val y: String, // y가 위도
    @SerializedName("place_url") val place_url: String,
    @SerializedName("distance") val distance: String
)
