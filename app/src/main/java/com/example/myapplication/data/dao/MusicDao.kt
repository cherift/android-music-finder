package com.example.myapplication.data.dao

import androidx.room.*
import com.example.myapplication.data.entity.MusicEntity
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface MusicDao {

    @Query("SELECT * FROM musics")
    fun findAllFavouriteMusics() : Flowable<List<MusicEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(music: MusicEntity) : Completable

    @Delete
    fun delete(music: MusicEntity) : Completable

    @Query("SELECT * FROM musics WHERE id LIKE :id LIMIT 1")
    fun findMusicById(id: String) : MusicEntity
}