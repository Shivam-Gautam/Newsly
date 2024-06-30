package com.simulation.newsly

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import com.simulation.newsly.api.NewsApiInterface
import com.simulation.newsly.api.RetrofitHelper
import com.simulation.newsly.database.ArticleDatabase
import com.simulation.newsly.fragments.FavoritesFragment
import com.simulation.newsly.fragments.NewsFragment
import com.simulation.newsly.fragments.SearchFragment
import com.simulation.newsly.repositories.NewsRepository
import com.simulation.newsly.viewmodel.NewsViewModel
import com.simulation.newsly.viewmodel.NewsViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var viewmodel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setListeners()

        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelFactory(repository)

        viewmodel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
    }

    private fun initViews(){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout,NewsFragment())
            commit()
        }
    }

    private fun setListeners(){
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.navBar)
        bottomNavigationView.setOnItemSelectedListener {item ->
            val clickedFragment: Fragment = when(item.itemId){
                R.id.news -> NewsFragment()
                R.id.search -> SearchFragment()
                R.id.favorites -> FavoritesFragment()
                else -> NewsFragment()
            }
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout,clickedFragment)
                commit()
            }
            true
        }

    }
}