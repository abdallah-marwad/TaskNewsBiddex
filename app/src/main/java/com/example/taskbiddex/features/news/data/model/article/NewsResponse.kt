package com.example.taskbiddex.features.news.data.model.article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val code: String,
    val message: String,
    val totalResults: Int
)