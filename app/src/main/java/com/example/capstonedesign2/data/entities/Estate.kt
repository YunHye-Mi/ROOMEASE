package com.example.capstonedesign2.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EstateTable")
data class Estate(
    @PrimaryKey(autoGenerate = false) var id: String, // 매물번호
    var deposit: String, // 보증금
    var address: String, // 주소
    var floor: String, // 층수
    var coverImg: String, // 이미지
    var rent: String, // 월세 가격
    var manage_cost: String, // 관리비
    var service_type: String, //
    var sales_type: String, //전월세 유형
    var size: String // 평수
)