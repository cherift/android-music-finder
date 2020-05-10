package com.example.myapplication.presentation.repository.remote

import com.example.myapplication.data.api.model.Music
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.api.model.MusicReader
import com.example.myapplication.data.api.model.MusicReaderResponse
import com.example.myapplication.data.api.service.MusicFinderService
import io.reactivex.Single

class MusicFinderDataRepository : MusicFinderRepository {

    private val apiService = MusicFinderService()

    override fun searchMusic(text: String) : Single<MusicFinderResponse>{
       return  this.apiService.searchMusics(text)
    }

    override fun getMusicReader(music: Music) : Single<MusicReaderResponse> {
        return this.apiService.getMusicReader(music.artist, music.title)
    }
}