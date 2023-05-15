package com.example.capstonedesign2.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AddressTable")
data class Address(
    var address: String,
    @PrimaryKey(autoGenerate = false)
    var detail: String,
    var latitude: Double,
    var longitude: Double
)