package com.example.capstonedesign2.data.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchRetrofitInterface {
    @Headers("Content-Type: application/json")
    @GET("/api/keyword")
    fun getKeyword(@Query("word") query: String): Call<List<SearchResponse>>

    @Headers("Content-Type: application/json")
    @GET("/api/list")
    fun getResultList(@Query("addr") address: String, @Query("count") count: Int?, @Query("sort") sort: Int?): Call<List<ResultResponse>>
}