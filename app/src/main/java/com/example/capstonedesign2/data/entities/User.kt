package com.example.capstonedesign2.data.entities

data class User(
    var accessToken: String,
    var refreshToken: String,
    var nickname: String,
    var registerNumber: String?,
    var role: String
)
