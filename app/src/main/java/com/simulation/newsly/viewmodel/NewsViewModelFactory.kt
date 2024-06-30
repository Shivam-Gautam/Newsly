package com.simulation.newsly.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.simulation.newsly.repositories.NewsRepository

class NewsViewModelFactory(private val newsRepository: NewsRepository ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}
