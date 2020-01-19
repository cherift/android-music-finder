package com.example.myapplication.presentation.presenter

import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.entity.MusicEntity


interface MusicFinderContrat {

    interface Presenter {

        fun attachView(view: View)

        fun detachView()

        fun cancelSubscription()

    }

    interface View {

        fun displayMusicFinded(musicResponse : MusicFinderResponse)

        fun addOrRemoveMusicFavorite(musicEntity: MusicEntity)

        fun listenToMusic()

        fun displayFavouriteMusic(musicEntities : List<MusicEntity>)
    }

}