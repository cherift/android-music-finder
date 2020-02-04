package com.example.myapplication.presentation.viewholder

import android.media.Image
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.presentation.display.MusicActionInterface

class FavouriteViewHolder(val myView: View, musicAction: MusicActionInterface) : RecyclerView.ViewHolder(myView) {

    var author: TextView? = null
    var title: TextView? = null
    var image: ImageView? = null
    var imageInfo: ImageButton? = null
    var imageButton: ImageButton? = null


    /**
     * initalizes card view element
     */
    init {
        author = myView.findViewById(R.id.fav_auhtor_textview)
        title = myView.findViewById(R.id.fav_title_textview)
        image = myView.findViewById(R.id.fav_icon_imageview)
        imageInfo = myView.findViewById(R.id.fav_info_button)
        imageButton = myView.findViewById(R.id.fav_image_button)

        imageInfo!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val position : Int = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    musicAction.onFavoriteToggle(position)
                }
            }
        })

        imageButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val position : Int = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    musicAction.listenMusic(position)
                }
            }
        })
    }


    /**
     * Sets music card view elements.
     *
     * @param author the autor text
     * @param title the title text
     * @param imageURL the iamge url
     */
    fun bind(author: String, title: String, imageURL: String){
        this.title!!.text = title
        this.author!!.text = author
        Glide
            .with(myView)
            .load(imageURL)
            .into(this.image!!)

    }
}