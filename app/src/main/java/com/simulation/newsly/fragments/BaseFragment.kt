package com.simulation.newsly.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.simulation.newsly.MainActivity
import com.simulation.newsly.R
import com.simulation.newsly.adapters.NewsAdapter
import com.simulation.newsly.viewmodel.NewsViewModel

abstract class BaseFragment: Fragment() {
    protected lateinit var viewmodel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = (activity as MainActivity).viewmodel
        newsAdapter = NewsAdapter()

        newsAdapter.setOnItemClickListener {
            val articleFragment = ArticleFragment.newInstance(it)

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.frameLayout,articleFragment)
                addToBackStack(null)
                commit()
            }
        }
    }
}