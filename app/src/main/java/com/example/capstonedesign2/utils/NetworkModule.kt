package com.example.capstonedesign2.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL1 = "http://3.39.130.73:8080"
const val BASE_URL2 = "http://3.35.4.170:80"

fun getRetrofit(): Retrofit {

    return Retrofit.Builder().baseUrl(BASE_URL1)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun getRetrofit2(): Retrofit {
    return  Retrofit.Builder().baseUrl(BASE_URL2)
        .addConverterFactory(GsonConverterFactory.create()).build()
}