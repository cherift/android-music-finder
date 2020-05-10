package com.example.myapplication.presentation.presenter

import com.example.myapplication.data.api.model.Music
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

class SearchMusicPresenter : MusicFinderContrat.Presenter<MusicFinderContrat.View>() {

    /**
     * Looks for music having lyrics corresponding to the text passed as parameter.
     *
     * @param text: the text to look for in the lyric
     */
    fun searchMusic(text: String){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        val musicfinder = MusicFinderDataRepository()

        compositeDisposable.clear()

        compositeDisposable.add(musicfinder.searchMusic(text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<MusicFinderResponse>() {
                override fun onSuccess(musics : MusicFinderResponse) {
                    view?.displayMusicFounded(musics)
                }

                override fun onError(e: Throwable) {
                    println(e.message)
                }
            })
        )
    }

    /**
     * Adds a music in the database.
     *
     * @param musicDao : the music DAO model
     * @param musicEntity : the music entity
     */
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

    /**
     * Removes a music in the database.
     *
     * @param musicDao : the music DAO model
     * @param musicEntity : the music entity
     */
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


    /**
     * Gets all musics saved in database.
     *
     * @param musicDao : the music DAO model
     */
    fun getFavouriteMusics(musicDao: MusicDao){
        val compositeDisposable : CompositeDisposable = CompositeDisposable()

        compositeDisposable.clear()

        compositeDisposable.add(musicDao.findAllFavouriteMusics()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : ResourceSubscriber<List<MusicEntity>>() {

                override fun onNext(musics : List<MusicEntity>) {

                    val musicFinderResponse : MusicFinderResponse = MusicFinderResponse(musics as List<Music>)

                    view?.displayMusicFounded(musicFinderResponse)
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
}