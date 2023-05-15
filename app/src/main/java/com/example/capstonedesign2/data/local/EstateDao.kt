package com.example.capstonedesign2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.capstonedesign2.data.entities.Estate

@Dao
interface EstateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(estate: Estate)

    @Query("SELECT * FROM EstateTable")
    fun getEstates(): List<Estate>

    @Query("SELECT * FROM EstateTable WHERE address=:address")
    fun getEstate(address: String): List<Estate>
}
