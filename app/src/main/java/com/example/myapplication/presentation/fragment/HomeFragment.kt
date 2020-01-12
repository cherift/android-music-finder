package com.example.myapplication.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.model.Music
import com.example.myapplication.presentation.adapter.MusicAdapter


/**
 * Home Fragemnt class
 */
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() : HomeFragment = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView : View = inflater.inflate(R.layout.frangment_menu, container, false)
        val recyclerView : RecyclerView = rootView.findViewById(R.id.home_recyclerview)

        val m1 = Music("dlfsjdlfksf", "fsdfsf", "dsfsfsdf", "fsdfsdfsdf")
        val m2 = Music("dlfsjdlfksf", "fsdfsf", "dsfsfsdf", "fsdfsdfsdf")

        var myDataset : List<Music> = listOf(m1, m2)

        val viewManager = LinearLayoutManager(activity)

        recyclerView.layoutManager = viewManager

        val musicAdapter: MusicAdapter = MusicAdapter(myDataset)

        recyclerView.adapter = musicAdapter

        return rootView
    }

}