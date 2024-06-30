package com.simulation.newsly.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.simulation.newsly.R
import com.simulation.newsly.util.Constants.Companion.QUERY_PAGE_SIZE
import com.simulation.newsly.util.Resource

class NewsFragment : BaseFragment() {

    private val TAG = "breakingnews"
    var isLoading = false
    var isLastPage = false
    var isScrolling = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewmodel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse?.articles?.toList())
                        val totalPages = (newsResponse?.totalResults?.div(QUERY_PAGE_SIZE) ?: 0) + 2
                        isLastPage = viewmodel.breakingNewsPage == totalPages
                        if (isLastPage) {
                            view.findViewById<RecyclerView>(R.id.rvBreakingNews)
                                .setPadding(0, 0, 0, 0)
                        }
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let { message ->
                        Log.e(TAG, "An error occurred: $ message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun setupRecyclerView(){
        val recyclerView : RecyclerView? = view?.findViewById(R.id.rvBreakingNews)
        recyclerView?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.scrollListener)
        }
    }

    private fun hideProgressBar(){
        val progressBar = view?.findViewById<ProgressBar>(R.id.paginationProgressBar)
        progressBar?.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar(){
        val progressBar = view?.findViewById<ProgressBar>(R.id.paginationProgressBar)
        progressBar?.visibility = View.VISIBLE
        isLoading = true
    }

    val scrollListener = object: RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewmodel.getBreakingNews("in")
                isScrolling = false
            }
        }
    }
}