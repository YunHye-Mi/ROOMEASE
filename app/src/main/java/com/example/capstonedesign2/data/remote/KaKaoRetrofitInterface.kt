package com.example.capstonedesign2.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
interface KaKaoRetrofitInterface {
     // 카카오 REST API 키를 설정합니다.
    @GET("/v2/local/search/category.json")
    fun searchPlacesByCategory(@Header("Authorization") key: String, @Query("category_group_code") groupCode: String, @Query("rect") rect: String): Call<ResultSearchKeyword>

    @GET("/v2/local/search/keyword.json")
    fun getSearchKeyword(@Header("Authorization") key: String, @Query("query") query: String, @Query("rect") rect: String?): Call<ResultSearchKeyword>    // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김
}