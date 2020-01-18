package com.example.myapplication.presentation.presenter

import com.example.myapplication.presentation.repository.MusicFinderDataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import com.example.myapplication.data.api.model.MusicFinderResponse

class SearchMusicPresenter : MusicFinderContrat.Presenter {

    var view : MusicFinderContrat.View? = null
    var compositeDisposable : CompositeDisposable? = null

    init {
        compositeDisposable = CompositeDisposable()
    }

    fun searchMusic(text: String){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        val musicfinder = MusicFinderDataRepository()

        compositeDisposable.clear()

        compositeDisposable.add(musicfinder.searchMusic(text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<MusicFinderResponse>() {
                override fun onSuccess(musics : MusicFinderResponse) {
                    view?.displayMusicFinded(musics)
                }

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }


    override fun attachView(view: MusicFinderContrat.View ) {
        this.view = view
    }

    override fun cancelSubscription() {
        compositeDisposable!!.clear()
    }

    override fun detachView() {
        compositeDisposable!!.dispose()
        view = null
    }
}