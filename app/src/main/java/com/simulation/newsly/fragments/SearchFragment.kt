package com.simulation.newsly.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simulation.newsly.MainActivity
import com.simulation.newsly.R
import com.simulation.newsly.adapters.NewsAdapter
import com.simulation.newsly.util.Constants
import com.simulation.newsly.util.Resource
import com.simulation.newsly.viewmodel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class SearchFragment : BaseFragment() {


    private var param1: String? = null
    private var param2: String? = null
    val TAG = "searchnewsfragment"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        var job: Job? = null

        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        etSearch.addTextChangedListener {editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(Constants.SEARCH_NEWS_TIME_DELAY)
                if(editable != null){
                    if(editable.toString().isNotEmpty()){
                        viewmodel.searchNews(editable.toString())
                    }
                }
            }
        }
        viewmodel.searchNews.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data.let {newsResponse ->
                        newsAdapter.differ.submitList(newsResponse?.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let {message ->
                        Log.e(TAG, "An error occurred: $ message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })


    }

    private fun setupRecyclerView(){
        val recyclerView : RecyclerView? = view?.findViewById(R.id.rvSearchNews)
        recyclerView?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar(){
        val progressBar = view?.findViewById<ProgressBar>(R.id.paginationProgressBarSearch)
        progressBar?.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        val progressBar = view?.findViewById<ProgressBar>(R.id.paginationProgressBarSearch)
        progressBar?.visibility = View.VISIBLE
    }
}