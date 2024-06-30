package com.simulation.newsly.api

import com.simulation.newsly.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val newsApi by lazy {
        getInstance().create(NewsApiInterface::class.java)
    }
}