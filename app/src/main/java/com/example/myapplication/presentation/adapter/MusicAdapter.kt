package com.example.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.model.Music
import com.example.myapplication.presentation.viewholder.MusicViewHolder

class MusicAdapter(val myDataset: List<Music>) : RecyclerView.Adapter<MusicViewHolder>() {


    /**
     * Creates new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MusicViewHolder {

        val textView = LayoutInflater.from(parent.context)
                                    .inflate(R.layout.music_card, parent, false) as View
        return MusicViewHolder(textView)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val music : Music = myDataset[position]
        holder.bind(music.author, music.title, music.imageURL)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = myDataset.size
}