package com.example.myapplication.presentation

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.presentation.fragment.FavouriteFragment
import com.example.myapplication.presentation.fragment.HomeFragment
import com.example.myapplication.presentation.fragment.InfoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* initialize fragment */
        replaceFragment(HomeFragment.newInstance())

        setupNavigationElements()
    }


    /**
     * Displays the fragment passed as parameter by replacing it by the last one
     *
     * @param fragment : the fragment to display
     */
    fun replaceFragment(fragment: Fragment?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragment?.let { fragmentTransaction.replace(R.id.fragment_container, it) }
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }


    /**
     * Accourding to the navigation button selected, this method sets the appropriate fragment.
     */
    fun setupNavigationElements() {

        val  navigationButton : BottomNavigationView = findViewById(R.id.navigation)

        navigationButton.setOnNavigationItemSelectedListener(setOnNavigationItemSelectedListener)

    }

    private val setOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        itemMenu -> when (itemMenu.itemId) {
            R.id.menu_home -> {
                val fragment : Fragment = HomeFragment.newInstance()
                replaceFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_favorite -> {
                val fragment : Fragment = FavouriteFragment.newInstance()
                replaceFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_info -> {
                val fragment : Fragment = InfoFragment.newInstance()
                replaceFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            else -> return@OnNavigationItemSelectedListener false

        }
    }

}
