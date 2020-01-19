package com.example.myapplication.presentation.fragment

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.presentation.adapter.MusicAdapter
import com.example.myapplication.presentation.presenter.MusicFinderContrat
import com.example.myapplication.presentation.presenter.SearchMusicPresenter
import java.util.*


/**
 * Home Fragemnt class
 */
class HomeFragment : Fragment(), MusicFinderContrat.View {

    var rootView : View? = null
    var progressBar : ProgressBar? = null
    var recyclerView : RecyclerView? = null
    var musicAdapter: MusicAdapter? = null
    var changeLayoutManager : ImageButton? = null

    companion object {
        val searchMusicPresenter: SearchMusicPresenter = SearchMusicPresenter()

        fun newInstance() : HomeFragment = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        rootView = inflater.inflate(R.layout.frangment_menu, container, false)

        progressBar = rootView!!.findViewById(R.id.progress_bar)

        changeLayoutManager = rootView!!.findViewById(R.id.change_layout_button)

        setupRecyclerView()

        return rootView
    }

    fun setupRecyclerView() {
        recyclerView = rootView!!.findViewById(R.id.home_recyclerview)
        musicAdapter = MusicAdapter(fragmentManager!!)
        recyclerView!!.adapter = musicAdapter
        val viewManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = viewManager

        changeLayoutManager = rootView!!.findViewById(R.id.change_layout_button)

        changeLayoutManager!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (recyclerView!!.layoutManager is GridLayoutManager) {
                    recyclerView!!.layoutManager = LinearLayoutManager(activity)
                    changeLayoutManager!!.setImageResource(R.drawable.grid_view)
                } else {
                    recyclerView!!.layoutManager = GridLayoutManager(activity, 2)
                    changeLayoutManager!!.setImageResource(R.drawable.no_grid_view)
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchMusicPresenter.attachView(this)

        val searchView = rootView!!.findViewById(R.id.search_view) as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            private var timer : Timer = Timer()

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length === 0) {
                    searchMusicPresenter.cancelSubscription()
                    progressBar!!.visibility = View.GONE
                }
                else {

                    progressBar!!.visibility = View.VISIBLE

                    timer.cancel()
                    timer = Timer()

                    var sleep : Long = 350

                    timer.schedule(object : TimerTask() {

                        override fun run() {
                            searchMusicPresenter.searchMusic(query)
                        }

                    }, sleep)
                }
                searchView.clearFocus()
                return true
            }

        })
    }

    override fun displayMusicFinded(musicResponse : MusicFinderResponse) {
        progressBar!!.visibility = View.GONE
        musicAdapter!!.bindViewModels(musicResponse)
    }

    override fun addMusicToFavorite() {

    }

    override fun listenToMusic() {

    }

}