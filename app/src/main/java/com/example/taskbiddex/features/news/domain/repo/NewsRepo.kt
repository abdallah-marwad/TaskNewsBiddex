package com.example.taskbiddex.features.news.domain.repo

import com.example.taskbiddex.common.utils.Resource
import com.example.taskbiddex.features.news.domain.entity.article.NewsResponse

interface NewsRepoAbstract  {
    suspend fun getAllNews(page :  Int): Resource<NewsResponse?>
}