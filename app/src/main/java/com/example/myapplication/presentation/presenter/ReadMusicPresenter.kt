package com.example.myapplication.presentation.presenter

import com.example.myapplication.data.api.model.Music
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.api.model.MusicReader
import com.example.myapplication.data.api.model.MusicReaderResponse
import com.example.myapplication.presentation.repository.remote.MusicFinderDataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ReadMusicPresenter : MusicFinderContrat.Presenter<MusicFinderContrat.ReaderView>() {

    /**
     * Looks for music song url.
     *
     * @param music: the music to read
     */
    fun getMusicReader(music: Music){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        val musicfinder = MusicFinderDataRepository()

        compositeDisposable.clear()

        compositeDisposable.add(musicfinder.getMusicReader(music)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<MusicReaderResponse>() {
                override fun onSuccess(musicReader : MusicReaderResponse) {
                    view?.displayMusicFounded(musicReader)
                }

                override fun onError(e: Throwable) {
                    view?.displayError()
                }
            })
        )
    }
}