package com.example.myapplication.data.api.model

/**
 *  {
 *      "author"  : author,
 *      "title"   : title,
 *      "image"   : image,
 *      "preview" : preview
 *  }
 */
data class MusicReader(
    val author: String,
    val title: String,
    val image: String,
    val preview: String
)