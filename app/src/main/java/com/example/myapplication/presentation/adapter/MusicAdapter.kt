package com.example.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.model.Music
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.presentation.viewholder.MusicViewHolder

class MusicAdapter(val fragmentManager: FragmentManager) : RecyclerView.Adapter<MusicViewHolder>() {

    var musics : List<Music> = listOf<Music>()


    /**
     * Creates new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MusicViewHolder {

        val textView = LayoutInflater.from(parent.context)
                                    .inflate(R.layout.music_card, parent, false) as View
        return MusicViewHolder(textView)
    }

    fun bindViewModels(musicResponse: MusicFinderResponse){
        musics = musicResponse.musics
        notifyDataSetChanged()
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music : Music = musics[position]
        holder.bind(music.artist, music.title, music.image)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = musics.size
}