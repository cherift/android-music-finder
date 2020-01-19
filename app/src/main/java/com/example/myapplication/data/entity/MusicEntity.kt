package com.example.myapplication.data.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musics")
data class MusicEntity(
    @NonNull
    @PrimaryKey
    val id: String,

    val title: String,

    val artist: String,

    val image: String
)