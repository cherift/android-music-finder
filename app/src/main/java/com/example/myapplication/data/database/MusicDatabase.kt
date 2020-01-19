package com.example.myapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.dao.MusicDao
import com.example.myapplication.data.entity.MusicEntity


@Database(entities = arrayOf(MusicEntity::class), version = 2)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDao(): MusicDao

    companion object {
        @Volatile
        private var INSTANCE : MusicDatabase? = null

        fun getInstance(context: Context): MusicDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicDatabase::class.java,
                    "database.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}