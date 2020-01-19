package com.example.myapplication.data.dao

import androidx.room.*
import com.example.myapplication.data.entity.MusicEntity

@Dao
interface MusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(music: MusicEntity)

    @Delete
    fun delete(music: MusicEntity)

    @Query("SELECT * FROM musics WHERE id LIKE :id LIMIT 1")
    fun findMusicById(id: String) : MusicEntity
}