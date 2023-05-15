package com.example.capstonedesign2.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BookmarkTable")
data class Bookmark(
    var userId : Int,
    var estateId : Int
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
