package com.example.myapplication.presentation.viewholder

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.entity.MusicEntity
import com.example.myapplication.presentation.display.MusicActionInterface

class MusicViewHolder(val myView: View, musicAction: MusicActionInterface) : RecyclerView.ViewHolder(myView) {

    var author: TextView? = null
    var title: TextView? = null
    var image: ImageView? = null
    var imageInfo: ImageButton? = null


    /**
     * initalizes card view element
     */
    init {
        author = myView.findViewById(R.id.auhtor_textview)
        title = myView.findViewById(R.id.title_textview)
        image = myView.findViewById(R.id.icon_imageview)
        imageInfo = myView.findViewById(R.id.info_button)

        imageInfo!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val tag = imageInfo!!.getTag()

                /**
                 * An id of a music entity is the concatenation of the author name, the title
                 *
                 * With that we can find rapidly the music entity in every fragment.
                 */
                val id: String = author!!.text.toString() + title!!.text.toString()

                val musicEntity = MusicEntity(id, author!!.text.toString(), title!!.text.toString(), image!!.toString())

                musicAction.onFavoriteToggle(musicEntity)
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