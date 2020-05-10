package com.example.myapplication.presentation.presenter

import com.example.myapplication.data.api.model.Music
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.api.model.MusicReader
import com.example.myapplication.data.api.model.MusicReaderResponse
import com.example.myapplication.data.entity.MusicEntity
import io.reactivex.disposables.CompositeDisposable


interface MusicFinderContrat {

    abstract class Presenter<V> {

        var view : V? = null
        var compositeDisposable : CompositeDisposable? = null

        init {
            compositeDisposable = CompositeDisposable()
        }

        /**
         * Attaches view in the presenter.
         */
        fun attachView(view: V) {
            this.view = view
        }

        /**
         * Clears the composite disposable.
         */
        fun cancelSubscription() {
            compositeDisposable!!.clear()
        }

        /**
         * Makes the composite disposable disposing.
         */
        fun detachView() {
            compositeDisposable!!.dispose()
            view = null
        }

    }

    interface View {

        fun displayMusicFounded(musicResponse : MusicFinderResponse)

        fun addOrRemoveMusicFavorite(musicEntity: MusicEntity)

        fun listenToMusic(music: Music)
    }

    interface ReaderView {

        fun displayMusicFounded(musicResponse: MusicReaderResponse)

        fun listenToMusic(musicReader: MusicReader)
    }

}