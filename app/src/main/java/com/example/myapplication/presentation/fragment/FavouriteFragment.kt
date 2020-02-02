package com.example.myapplication.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.model.Music
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.dao.MusicDao
import com.example.myapplication.data.database.MusicDatabase
import com.example.myapplication.data.entity.MusicEntity
import com.example.myapplication.presentation.adapter.FavouriteAdapter
import com.example.myapplication.presentation.adapter.MusicAdapter
import com.example.myapplication.presentation.presenter.MusicFinderContrat
import com.example.myapplication.presentation.presenter.SearchMusicPresenter
import com.example.myapplication.presentation.repository.local.LocalMusicRepository
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber




/**
 * Favourite fragement class
 */
class FavouriteFragment : Fragment(), MusicFinderContrat.View{

    var rootView : View? = null
    var recyclerView : RecyclerView? = null
    var favouriteAdapter: FavouriteAdapter? = null
    var musicDao:MusicDao? = null

    companion object {
        val searchMusicPresenter: SearchMusicPresenter = SearchMusicPresenter()

        fun newInstance() : FavouriteFragment = FavouriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.fragment_favourite, container, false)

        /*initialize local database repository*/
        musicDao = MusicDatabase.getInstance(activity!!.application).musicDao()

        return rootView
    }

    fun setupRecyclerView() {
        recyclerView = rootView!!.findViewById(R.id.fav_home_recyclerview)
        favouriteAdapter = FavouriteAdapter(this)
        recyclerView!!.adapter = favouriteAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()

        searchMusicPresenter.attachView(this)
        searchMusicPresenter.getFavouriteMusics(musicDao!!)
    }


    override fun onDestroy() {
        super.onDestroy()
        searchMusicPresenter.detachView()
    }

    override fun displayMusicFinded(musicResponse : MusicFinderResponse) {

    }


    override fun addOrRemoveMusicFavorite(musicEntity: MusicEntity) {
        searchMusicPresenter.removeMusicFromFavorite(musicDao!!, musicEntity)

        // Show music removed from favourite message
        Snackbar.make(
            recyclerView!!,
            resources.getString(R.string.music_removed, musicEntity.artist, musicEntity.title),
            Snackbar.LENGTH_SHORT
        ).show()
    }


    override fun listenToMusic() {

    }

    override fun displayFavouriteMusic(musicEntities : List<MusicEntity>){
        favouriteAdapter!!.bindViewModels(musicEntities)
    }
}