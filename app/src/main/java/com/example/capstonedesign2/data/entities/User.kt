package com.example.capstonedesign2.data.entities

data class User(
    var id: Int,
    var token: String,
    var nickname: String,
    var registerNumber: String?,
    var role: String
)
