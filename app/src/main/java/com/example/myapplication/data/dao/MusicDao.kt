package com.example.myapplication.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.myapplication.data.entity.MusicEntity

@Dao
interface MusicDao {
    @Insert
    fun insert(music: MusicEntity)

    @Delete
    fun delete(music: MusicEntity)

    @RawQuery
    fun getMusicViaQuery(query: SupportSQLiteQuery) : MusicEntity
}