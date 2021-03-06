package com.example.myapplication.data.api.model

import com.google.gson.annotations.SerializedName

data class MusicFinderResponse(
    @SerializedName("result") val musics: List<Music>
)