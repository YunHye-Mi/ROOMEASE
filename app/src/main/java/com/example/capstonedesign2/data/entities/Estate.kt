package com.example.capstonedesign2.data.entities

import androidx.room.Entity

@Entity(tableName = "EstateTable")
data class Estate(
    var name: String? = "",
    var address: String? = "",
    var coverImgList: List<Int>,
    var rent_gbn: String? = "",
    var rent_area: Double? = 0.0,
    var rent_gtn: String? = "",
    var rent_fee: String? = "",
    var flr_no: Int? = 0
)