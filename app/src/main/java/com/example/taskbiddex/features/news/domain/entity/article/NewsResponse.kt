package com.example.taskbiddex.features.news.domain.entity.article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val code: String,
    val message: String,
    val totalResults: Int
)