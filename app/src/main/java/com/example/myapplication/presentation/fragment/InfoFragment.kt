package com.example.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R


/**
 * Info fragment class
 */
class InfoFragment : Fragment() {

    companion object {
        fun newInstance() : InfoFragment = InfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frangment_infos, container, false)
    }
}