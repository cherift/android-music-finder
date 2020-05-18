package com.example.myapplication.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.model.Music
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.dao.MusicDao
import com.example.myapplication.data.database.MusicDatabase
import com.example.myapplication.data.entity.MusicEntity
import com.example.myapplication.presentation.adapter.FavouriteAdapter
import com.example.myapplication.presentation.presenter.MusicFinderContrat
import com.example.myapplication.presentation.presenter.SearchMusicPresenter
import com.google.android.material.snackbar.Snackbar




/**
 * Favourite fragement class
 */
class FavouriteFragment : Fragment(), MusicFinderContrat.View{

    var rootView : View? = null
    var recyclerView : RecyclerView? = null
    var favouriteAdapter: FavouriteAdapter? = null
    var emptyTextView : TextView? = null
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

        emptyTextView = rootView!!.findViewById(R.id.empty)

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


    /**
     * Displays  the list of favourite music in the recycler view.
     * If the list is empty a message view will be displayed.
     *
     * @param musicEntities : the list of favourite music entities
     */
    override fun displayMusicFounded(musicResponse : MusicFinderResponse) {
        if (musicResponse.musics.isEmpty()) {
            emptyTextView?.visibility = View.VISIBLE
            recyclerView?.visibility = View.GONE
        }
        else{
            emptyTextView?.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
            favouriteAdapter!!.bindViewModels(musicResponse.musics as List<MusicEntity>)
        }
    }


    /**
     * Removes a music entity from musics database.
     *
     * @param musicEntity : the music to remove
     */
    override fun addOrRemoveMusicFavorite(musicEntity: MusicEntity) {
        searchMusicPresenter.removeMusicFromFavorite(musicDao!!, musicEntity)

        // Show music removed from favourite message
        Snackbar.make(
            recyclerView!!,
            resources.getString(R.string.music_removed, musicEntity.artist, musicEntity.title),
            Snackbar.LENGTH_SHORT
        ).show()
    }


    /**
     * Reads the music passed as parameter
     *
     * @param music : the music to read
     */
    override fun listenToMusic(music: Music) {
        activity!!.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MusicPlayerFragment.newInstance(music))
            .addToBackStack(null)
            .commit()
    }

    /**
     * This functin must show an error because of not internet connection detected.
     *
     * But this fragment does not need this so it will return Unit
     */
    override fun displayError() = Unit
}