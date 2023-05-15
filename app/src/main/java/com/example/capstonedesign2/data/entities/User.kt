package com.example.capstonedesign2.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserTable")
data class User(
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    var token: String,
    var nickname: String,
    var registerNumber: String?,
    var role: String
)
