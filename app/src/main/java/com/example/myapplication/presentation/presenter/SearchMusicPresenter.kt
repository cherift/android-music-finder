package com.example.myapplication.presentation.presenter

import com.example.myapplication.presentation.repository.remote.MusicFinderDataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.dao.MusicDao
import com.example.myapplication.data.entity.MusicEntity
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.subscribers.ResourceSubscriber

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

    fun addMusicToFavorite(musicDao: MusicDao, musicEntity: MusicEntity) {
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        compositeDisposable.clear()

        compositeDisposable.add(musicDao.insert(musicEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {

                override fun onComplete() {
                    print("adding to data base")
                }

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }


    fun removeMusicFromFavorite(musicDao: MusicDao, musicEntity: MusicEntity) {
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        compositeDisposable.clear()

        compositeDisposable.add(musicDao.delete(musicEntity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {

                override fun onComplete() {
                    println("deleting music from database")
                }

                override fun onError(e: Throwable) {
                    println("deleting music from database failed")
                    println(e.message)
                }
            })
        )
    }



    fun getFavouriteMusics(musicDao: MusicDao){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        compositeDisposable.clear()

        compositeDisposable.add(musicDao.findAllFavouriteMusics()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : ResourceSubscriber<List<MusicEntity>>() {

                override fun onNext(musics : List<MusicEntity>) {
                    view?.displayFavouriteMusic(musics  )
                }

                override fun onComplete() {
                    print("adding to data base")
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