package com.example.myapplication.presentation.viewholder

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R

class MusicViewHolder(val myView: View) : RecyclerView.ViewHolder(myView) {

    var author: TextView? = null
    var title: TextView? = null
    var image: ImageView? = null
    var imageButton: ImageButton? = null


    /**
     * initalizes card view element
     */
    init {
        author = myView.findViewById(R.id.auhtor_textview)
        title = myView.findViewById(R.id.title_textview)
        image = myView.findViewById(R.id.icon_imageview)
        imageButton = myView.findViewById(R.id.info_button)

        imageButton?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                TODO("not implemented")
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