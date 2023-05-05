package com.example.capstonedesign2.data.entities

data class User(
    var id : Int,
    var token: String,
    var nickname: String = "",
    var hope_condition: ArrayList<String>,
    var inter_num: String? = "" // 공인중개사 번호
)
