package com.simulation.newsly.repositories

import com.simulation.newsly.api.RetrofitHelper
import com.simulation.newsly.database.ArticleDatabase
import com.simulation.newsly.model.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) = RetrofitHelper.newsApi.getNewsResponse(countryCode,pageNumber)

    suspend fun searchNews(query: String, pageNumber: Int) = RetrofitHelper.newsApi.searchNewsResponse(query,pageNumber)

    suspend fun upsertArticle(article: Article) = db.getArticleDao().upsert(article)

    fun getSavedNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().delete(article)
}