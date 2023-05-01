package com.example.capstonedesign2.data.entities

import androidx.room.Entity

@Entity(tableName = "reviewTable")
data class Review(
    var user: String? = "",
    var target: String? = "",
    var write_date: Long,
    var period: String? = "",
    var age: String? = "",
    var gender: String? = "",
    var public_rate: Int,
    var ambient_rate: Int,
    var lived_rate: Int,
    var content: String? = ""
)