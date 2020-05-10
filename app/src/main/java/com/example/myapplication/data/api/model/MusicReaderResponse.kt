package com.example.myapplication.data.api.model

import com.google.gson.annotations.SerializedName

data class MusicReaderResponse (
    @SerializedName("result") val musicReader: MusicReader
)