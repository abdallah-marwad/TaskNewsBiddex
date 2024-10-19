package com.example.taskbiddex.features.news.data.model.article

import java.io.Serializable


class Article(
    var id: Int = 0,
    val author: String?,
    var content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable