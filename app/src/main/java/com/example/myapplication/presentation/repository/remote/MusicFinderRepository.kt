package com.example.myapplication.presentation.repository.remote

import com.example.myapplication.data.api.model.MusicFinderResponse
import io.reactivex.Single

interface MusicFinderRepository {

    fun searchMusic(text: String) : Single<MusicFinderResponse>

}