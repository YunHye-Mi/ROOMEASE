package com.example.capstonedesign2.data.entities

import androidx.room.Entity

@Entity(tableName = "IntermediaryReviewTable")
data class IntermediaryReview(
    var userId : Int,
    var intermediaryId : Int,
    var rate : Double
)
