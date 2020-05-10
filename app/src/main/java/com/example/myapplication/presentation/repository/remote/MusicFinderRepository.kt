package com.example.myapplication.presentation.repository.remote

import com.example.myapplication.data.api.model.Music
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.api.model.MusicReader
import com.example.myapplication.data.api.model.MusicReaderResponse
import io.reactivex.Single

interface MusicFinderRepository {

    fun searchMusic(text: String) : Single<MusicFinderResponse>

    fun getMusicReader(music: Music) : Single<MusicReaderResponse>

}