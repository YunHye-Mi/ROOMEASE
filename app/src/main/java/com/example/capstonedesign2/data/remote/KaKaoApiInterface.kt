package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

data class PlaceResponse(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("same_name") val same_name: RegionInfo,
    @SerializedName("documents") val places: Place
)

data class ResultSearchKeyword(
    @SerializedName("meta") val meta: Meta, // 장소 메타데이터
    @SerializedName("documents") val documents: List<Place> // 검색 결과
)

data class Meta(
    @SerializedName("total_count") val total_count: Int,
    @SerializedName("pageable_count") val pageable_count: Int,
    @SerializedName("is_end") val is_end: Boolean,
    @SerializedName("same_name") val same_name: RegionInfo
)

data class RegionInfo(
    @SerializedName("region") val region: ArrayList<String>,
    @SerializedName("keyword") val keyword: String,
    @SerializedName("selected_region") val selected_region: String
)

data class Place(
    @SerializedName("id") val id: String,
    @SerializedName("place_name") val placeName: String,
    @SerializedName("category_name") val category_name: String,
    @SerializedName("category_group_code") val category_group_code: String,
    @SerializedName("category_group_name") val category_group_name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address_name") val addressName: String,
    @SerializedName("road_address_name") val road_address_name: String,
    @SerializedName("x") val x: String, // x가 경도
    @SerializedName("y") val y: String, // y가 위도
    @SerializedName("place_url") val place_url: String,
    @SerializedName("distance") val distance: String
)

data class PlaceSearch(
    val id: Int,
    val name: String,
    val road: String, // 도로명 주소
    val x: Double,
    val y: Double
)

interface KaKaoApiInterface {
     // 카카오 REST API 키를 설정합니다.
    @GET("/v2/local/search/keyword.json")
    fun searchPlacesByCategory(@Header("Authorization") key: String, @Query("category_group_code") groupCode: String, @Query("rect") rect: String): Call<PlaceResponse>

    @GET("v2/local/search/keyword.json")
    fun getSearchKeyword(@Header("Authorization") key: String, @Query("query") query: String, @Query("page") page: Int): Call<ResultSearchKeyword>    // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김
}