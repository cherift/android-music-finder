package com.example.myapplication.presentation.display

import com.example.myapplication.data.entity.MusicEntity

interface MusicActionInterface {

    fun onFavoriteToggle(musicEntity: MusicEntity)

}