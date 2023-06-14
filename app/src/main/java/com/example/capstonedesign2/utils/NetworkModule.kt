package com.example.capstonedesign2.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL1 = "1번째 멀티 서버 주소"
const val BASE_URL2 = "2번째 멀티 서버 주소"

fun getRetrofit(): Retrofit {

    return Retrofit.Builder().baseUrl(BASE_URL1)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun getRetrofit2(): Retrofit {
    return  Retrofit.Builder().baseUrl(BASE_URL2)
        .addConverterFactory(GsonConverterFactory.create()).build()
}