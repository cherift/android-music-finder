package com.example.myapplication.presentation.fragment


import android.media.MediaPlayer
import android.os.Bundle
import android.os.HandlerThread
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
    var playButton: ImageView? = null
    var mediaPlayer: MediaPlayer? = null

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
        playButton = rootView!!.findViewById(R.id.playButton)

        mediaPlayer = MediaPlayer()

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
        println("je suis ici image ${musicReader!!.image}")
        println("je suis ici preview ${musicReader!!.preview}")
        titleView!!.text = musicReader!!.title
        Glide.with(rootView!!)
            .load(musicReader!!.image)
            .into(thumbnailView!!)

        playButton!!.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {

                try {
                    mediaPlayer!!.setDataSource(musicReader!!.preview)
                }
                catch (e: Exception){
                    println("Setting music data source failed because of : ${e.message}")
                }

                mediaPlayer!!.prepareAsync()
                mediaPlayer!!.setOnPreparedListener( object : MediaPlayer.OnPreparedListener {
                    override fun onPrepared(mp: MediaPlayer?) {
                        mp?.start()
                    }
                })

            }

        })

    }

    /**
     * Allows to read the music.
     *
     * @param musicReader : the music to read
     */
    override fun listenToMusic(musicReader: MusicReader){

    }
}