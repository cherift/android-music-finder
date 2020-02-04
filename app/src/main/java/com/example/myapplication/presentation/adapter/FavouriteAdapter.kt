package com.example.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.model.Music
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.entity.MusicEntity
import com.example.myapplication.presentation.display.MusicActionInterface
import com.example.myapplication.presentation.fragment.FavouriteFragment
import com.example.myapplication.presentation.viewholder.FavouriteViewHolder
import com.example.myapplication.presentation.viewholder.MusicViewHolder

class FavouriteAdapter(val fragmentManager: FavouriteFragment) : RecyclerView.Adapter<FavouriteViewHolder>(),
    MusicActionInterface {

    var musics : MutableList<Music> = mutableListOf<Music>()

    /**
     * Creates new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): FavouriteViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_music_card, parent, false) as View
        return FavouriteViewHolder(view, this)
    }



    fun bindViewModels(musicEntities : List<MusicEntity>){
        musics.clear()

        for(music in musicEntities){
            musics.add(Music(music.title, music.artist, music.image))
        }

        notifyDataSetChanged()
    }


    /**
     * Deletes the music at the position passed as parameter
     *
     * @param position: the position of the music clicked in the list view
     */
    override fun onFavoriteToggle(position: Int){
        val music: Music = musics[position]

        val musicEntity: MusicEntity = MusicEntity(
            music.artist + music.title,
            music.title,
            music.artist,
            music.image
        )

        musics.removeAt(position)

        notifyItemRemoved(position)

        fragmentManager.addOrRemoveMusicFavorite(musicEntity)
    }


    /**
     * Listen to the music at the position passed as parameter
     *
     * @param position: the position of the music clicked in the list view
     */
    override fun listenMusic(position: Int){
        fragmentManager.listenToMusic()
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val music : Music = musics[position]
        holder.bind(music.artist, music.title, music.image)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = musics.size
}