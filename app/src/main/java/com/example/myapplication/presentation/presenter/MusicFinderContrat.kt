package com.example.myapplication.presentation.presenter

import com.example.myapplication.data.api.model.MusicFinderResponse


interface MusicFinderContrat {

    interface Presenter {

        fun attachView(view: View)

        fun detachView()

        fun cancelSubscription()

    }

    interface View {

        fun displayMusicFinded(musicResponse : MusicFinderResponse)

        fun addMusicToFavorite()

        fun listenToMusic()
    }

}