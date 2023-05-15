package com.example.capstonedesign2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.capstonedesign2.data.entities.*

@Database(entities = [Address::class, Estate::class, Bookmark::class, User::class], version = 1)
abstract class EstateDatabase: RoomDatabase() {
    abstract fun addressDao(): AddressDao
    abstract fun estateDao(): EstateDao
    abstract fun userDao(): UserDao
    abstract fun bookmarkDao(): BookmarkDao

    companion object{
        private var instance: EstateDatabase? = null

        @Synchronized
        fun getInstance(context: Context): EstateDatabase? {
            if (instance == null) {
                synchronized(EstateDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EstateDatabase::class.java,
                        "Estate-database"
                    ).allowMainThreadQueries().build()
                }
            }
            return instance
        }
    }
}