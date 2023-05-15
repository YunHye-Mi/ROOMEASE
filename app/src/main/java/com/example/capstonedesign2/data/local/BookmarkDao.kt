package com.example.capstonedesign2.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.capstonedesign2.data.entities.Address
import com.example.capstonedesign2.data.entities.Bookmark

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookmark: Bookmark)

    @Query("SELECT * FROM BookmarkTable Where id=:id")
    fun getBookmarks(id: Int): List<Bookmark>
}
