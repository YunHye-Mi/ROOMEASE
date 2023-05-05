package com.example.capstonedesign2.data.entities

import androidx.room.Entity

@Entity(tableName = "AddressTable")
data class Address(
    var address: String,
    var detail: String,
    var latitude: Double,
    var longitude: Double
)