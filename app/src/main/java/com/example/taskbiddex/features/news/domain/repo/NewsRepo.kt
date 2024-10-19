package com.example.taskbiddex.features.news.domain.repo

import com.example.taskbiddex.common.utils.Resource
import com.example.taskbiddex.features.news.data.model.article.Article
import com.example.taskbiddex.features.news.data.model.article.NewsResponse

interface NewsRepoAbstract  {
    suspend fun getAllNews(page :  Int): Resource<NewsResponse?>
}