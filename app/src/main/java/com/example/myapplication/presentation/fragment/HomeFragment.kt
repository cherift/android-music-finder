package com.example.myapplication.presentation.fragment

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.api.model.MusicFinderResponse
import com.example.myapplication.data.dao.MusicDao
import com.example.myapplication.data.database.MusicDatabase
import com.example.myapplication.data.entity.MusicEntity
import com.example.myapplication.presentation.adapter.MusicAdapter
import com.example.myapplication.presentation.presenter.MusicFinderContrat
import com.example.myapplication.presentation.presenter.SearchMusicPresenter
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.frangment_menu.view.*
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
    var musicDao: MusicDao? = null
    var emptyTextView : TextView? = null
    val MUSICS_PREFERENCES_KEY: String = "MUSICS_PREFERENCES_KEY"

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

        emptyTextView = rootView!!.findViewById(R.id.empty)

        progressBar = rootView!!.findViewById(R.id.progress_bar)

        changeLayoutManager = rootView!!.findViewById(R.id.change_layout_button)

        emptyTextView?.visibility = View.VISIBLE
        recyclerView?.visibility = View.GONE

        setupRecyclerView()

        /*initialize local database repository*/
        musicDao = MusicDatabase.getInstance(activity!!.application).musicDao()

        return rootView
    }

    private fun setupRecyclerView() {
        recyclerView = rootView!!.findViewById(R.id.home_recyclerview)
        musicAdapter = MusicAdapter(this)
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
                if (query.isEmpty()) {
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

        // Loads data in the shared preferences and displays it
        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("shared preferences", MODE_PRIVATE)
        val gson: Gson = Gson()
        var result : String? = sharedPreferences.getString(MUSICS_PREFERENCES_KEY, null)

        var musicResponse: MusicFinderResponse? = gson.fromJson(result, MusicFinderResponse::class.java)

        musicResponse?.let {
            musicAdapter!!.bindViewModels(it)
            emptyTextView?.visibility = View.GONE
            recyclerView?.visibility = View.VISIBLE
        }
    }


    /**
     * Saves musics founded in shared date preferences
     *
     * @param musicResponse: the list of musics founded
     */
    private fun saveData(musicResponse : MusicFinderResponse){
        val sharedPreferences: SharedPreferences = activity!!.getSharedPreferences("shared preferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit() as SharedPreferences.Editor

        val gson: Gson = Gson()
        var result : String? = gson.toJson(musicResponse)

        editor.putString(MUSICS_PREFERENCES_KEY, result)
        editor.apply()
    }


    /**
     * displays the musics founded in the list veiw
     *
     * @param musicResponse: the list of musics founded
     */
    override fun displayMusicFinded(musicResponse : MusicFinderResponse) {
        /*save musicResponse founded in musicSaved */
        saveData(musicResponse)

        /*displays the result in the list view*/
        progressBar!!.visibility = View.GONE
        musicAdapter!!.bindViewModels(musicResponse)
    }


    override fun addOrRemoveMusicFavorite(musicEntity: MusicEntity) {
        searchMusicPresenter.addMusicToFavorite(musicDao!!, musicEntity)

        // Show music added in favourite message
        Snackbar.make(
            recyclerView!!,
            resources.getString(R.string.music_added, musicEntity.artist, musicEntity.title),
            Snackbar.LENGTH_SHORT
        ).show()
    }


    override fun listenToMusic() {

    }

    override fun displayFavouriteMusic(musicEntities : List<MusicEntity>){
        
    }

}