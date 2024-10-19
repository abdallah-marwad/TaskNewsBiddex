package com.example.taskbiddex.features.news.data.remote

import com.example.taskbiddex.common.utils.Constant
import com.example.taskbiddex.features.news.data.model.article.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("v2/everything")
    suspend fun getAllNews(
        @Query("sources")
        category:String = "wired",
        @Query("page")
        pageNumber:Int =1,
        @Query("apiKey")
        apiKey:String = Constant.API_KEY,
    ): Response<NewsResponse>


}