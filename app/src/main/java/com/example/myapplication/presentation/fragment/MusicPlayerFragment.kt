package com.example.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.api.model.Music
import com.example.myapplication.data.api.model.MusicReader
import com.example.myapplication.data.api.model.MusicReaderResponse
import com.example.myapplication.presentation.presenter.MusicFinderContrat
import com.example.myapplication.presentation.presenter.ReadMusicPresenter

class MusicPlayerFragment(val music: Music) : Fragment(), MusicFinderContrat.ReaderView {

    var musicReader: MusicReader? = null
    var rootView : View? = null
    var authorView : TextView? = null
    var titleView : TextView? = null
    var thumbnailView: ImageView? = null

    companion object {
        val readMusicPresenter : ReadMusicPresenter = ReadMusicPresenter()

        fun newInstance(music: Music) : MusicPlayerFragment = MusicPlayerFragment(music)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.music_player, container, false)

        authorView = rootView!!.findViewById(R.id.auhtor)
        titleView = rootView!!.findViewById(R.id.title)
        thumbnailView = rootView!!.findViewById(R.id.thumbnail)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        readMusicPresenter.attachView(this)
        readMusicPresenter.getMusicReader(music)
        readMusicPresenter.cancelSubscription()
    }

    /**
     * Displays the music informations founded in the screen.
     *
     * @param musicResponse : the music informations founded
     */
    override fun displayMusicFounded(musicResponse: MusicReaderResponse){
        musicReader = musicResponse.musicReader

        authorView!!.text = musicReader!!.author
        titleView!!.text = musicReader!!.title
        Glide.with(rootView!!)
            .load(musicReader!!.image)
            .into(thumbnailView!!)
    }

    /**
     * Allows to read the music
     *
     * @param musicReader : the music to read
     */
    override fun listenToMusic(musicReader: MusicReader){

    }
}