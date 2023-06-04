package com.example.capstonedesign2.data.entities

data class Filter(
    val minRent: Double?,
    val maxRent: Double?,
    val type: Int,
    val minSize: Double?,
    val maxSize: Double?,
    val minDeposit: Int?,
    val maxDeposit: Int?,
    val minManage: Double?,
    val maxManage: Double?,
    val minFloor: Int,
    val maxFloor: Int
)
