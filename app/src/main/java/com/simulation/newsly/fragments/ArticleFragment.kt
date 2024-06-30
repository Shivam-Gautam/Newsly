package com.simulation.newsly.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.simulation.newsly.MainActivity
import com.simulation.newsly.R
import com.simulation.newsly.adapters.NewsAdapter
import com.simulation.newsly.model.Article
import com.simulation.newsly.viewmodel.NewsViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ArticleFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            article = it.getSerializable("article") as Article
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(article: Article) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("article",article)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewmodel
        val webView = view.findViewById<WebView>(R.id.webView)

        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        val fab : FloatingActionButton = view.findViewById<FloatingActionButton>(R.id.fab)


        fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article saved successfully",Snackbar.LENGTH_SHORT).show()
        }
    }
}