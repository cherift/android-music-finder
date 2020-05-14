package com.example.myapplication.presentation.fragment


import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
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
    var prevButton: ImageView? = null
    var nextButton: ImageView? = null
    var closeButton: ImageView? = null
    var progressBar: SeekBar? = null
    var musicDuration: Int = 0

    companion object {
        val readMusicPresenter : ReadMusicPresenter = ReadMusicPresenter()
        val mediaPlayer : MediaPlayer = MediaPlayer()
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
        prevButton = rootView!!.findViewById(R.id.prevButton)
        nextButton = rootView!!.findViewById(R.id.nextButton)
        progressBar = rootView!!.findViewById(R.id.progressBar)
        closeButton = rootView!!.findViewById(R.id.close_window)

        // Resets music if another one is playing from another fragment
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.reset()
        }

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

        try {
            mediaPlayer!!.setDataSource(musicReader!!.preview)
        }
        catch (e: Exception){
            println("Setting music data source failed because of : ${e.message}")
        }

        mediaPlayer!!.prepareAsync()
        mediaPlayer!!.setOnPreparedListener( object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp: MediaPlayer?) {
                musicDuration = mediaPlayer!!.duration
                progressBar!!.max = musicDuration
            }
        })

        // play music button event
        playButton!!.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                listenToMusic(mediaPlayer!!)
            }

        })

        // forwarding and backwarding music to 5 seconds
        prevButton!!.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                var progress: Int =  if ((progressBar!!.progress - 5000) < 0) 0 else progressBar!!.progress - 5000
                progressBar!!.progress = progress
                mediaPlayer!!.seekTo(progress)
            }

        })

        nextButton!!.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                var progress: Int =  if ((progressBar!!.progress + 5000) > musicDuration) musicDuration else progressBar!!.progress + 5000
                progressBar!!.progress = progress
                mediaPlayer!!.seekTo(progress)
            }

        })

        //when button close is pressed
        closeButton!!.setOnClickListener(object: View.OnClickListener {

            override fun onClick(v: View?) {
                mediaPlayer!!.reset()
                activity!!.supportFragmentManager!!.popBackStack()
            }

        })


        // managing progress bar
        progressBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    mediaPlayer!!.seekTo(progress)
                    progressBar!!.progress = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        /**
         * Thread that will send a each one second the current position of mediaPlayer
         * to the handler.
         */
        Thread(object: Runnable {
            override fun run() {
                while(mediaPlayer != null){
                     try {
                         val message : Message = Message()
                         message.what = mediaPlayer!!.currentPosition

                         handler.handleMessage(message)
                         Thread.sleep(1000)
                     }catch (e: InterruptedException) {}
                }
            }
        }).start()

    }

    /**
     * handler object that helps the progress to advance to the position of mediaPlayer.
     *
     * The postition will be passed as message.
     */
    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            progressBar!!.progress = msg.what as Int
        }
    }

    /**
     * Allows to read the music.
     *
     * @param music : the music to read
     */
    override fun listenToMusic(mp: MediaPlayer) {
        if (!mp.isPlaying) {
            mp.start()
            playButton!!.setBackgroundResource(R.drawable.pause)
        }
        else {
            mp.pause()
            playButton!!.setBackgroundResource(R.drawable.play)
        }

        mp.setOnCompletionListener(object: MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer?) {
                playButton!!.setBackgroundResource(R.drawable.play)
            }
        })
    }

}