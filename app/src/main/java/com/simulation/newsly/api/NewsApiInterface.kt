package com.simulation.newsly.api

import com.simulation.newsly.model.NewsResponse
import com.simulation.newsly.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale.IsoCountryCode


interface NewsApiInterface {

    @GET("v2/top-headlines")
    suspend fun getNewsResponse(
        @Query("country" ) countryCode: String = "us",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ) : Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNewsResponse(
        @Query("q" ) query: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ) : Response<NewsResponse>
}