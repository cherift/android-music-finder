package com.example.myapplication.presentation.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R


/**
 * Info fragment class
 */
class InfoFragment : Fragment() {

    var rootView : View? = null

    companion object {
        fun newInstance() : InfoFragment = InfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.frangment_infos, container, false)

        val githubLink : TextView = rootView!!.findViewById(R.id.github_link)
        githubLink.movementMethod = LinkMovementMethod.getInstance()

        return rootView
    }
}