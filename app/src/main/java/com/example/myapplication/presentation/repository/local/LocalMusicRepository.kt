package com.example.myapplication.presentation.repository.local

import com.example.myapplication.data.dao.MusicDao
import com.example.myapplication.data.entity.MusicEntity

class LocalMusicRepository(private val musicDao: MusicDao) {

    suspend fun insert(musicEntity: MusicEntity){
        musicDao.insert(musicEntity)
    }

    suspend fun delete(musicEntity: MusicEntity){
        musicDao.delete(musicEntity)
    }

    suspend fun getMusic(id : String) : MusicEntity {
        return musicDao.findMusicById(id)
    }

}