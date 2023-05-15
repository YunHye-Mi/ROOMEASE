package com.example.capstonedesign2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.capstonedesign2.data.entities.Address

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: Address)

    @Query("SELECT * FROM AddressTable")
    fun getAddresses(): List<Address>
}
