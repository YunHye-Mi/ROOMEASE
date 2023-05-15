package com.example.capstonedesign2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.capstonedesign2.data.entities.User

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM UserTable WHERE id=:id")
    fun getUser(id: Int): User?
}
