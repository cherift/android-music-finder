package com.example.myapplication.presentation.repository.remote

import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.api.service.MusicFinderService
import io.reactivex.Single

class MusicFinderDataRepository :
    MusicFinderRepository {

    private val apiService = MusicFinderService()

    override fun searchMusic(text: String) : Single<MusicFinderResponse>{
       return  this.apiService.searchMusics(text)
    }
}