package com.example.myapplication.presentation.repository.local

import com.example.myapplication.data.dao.MusicDao
import com.example.myapplication.data.entity.MusicEntity
import io.reactivex.Completable
import io.reactivex.Flowable

class LocalMusicRepository(private val musicDao: MusicDao) {

    fun insert(musicEntity: MusicEntity) : Completable = run {
        musicDao.insert(musicEntity)
    }

    fun delete(musicEntity: MusicEntity) : Completable = run {
        musicDao.delete(musicEntity)
    }

    fun getMusic(id : String) : MusicEntity {
        return musicDao.findMusicById(id)
    }

    fun findAllFavouriteMusics() : Flowable<List<MusicEntity>> {
        return musicDao.findAllFavouriteMusics()
    }

}