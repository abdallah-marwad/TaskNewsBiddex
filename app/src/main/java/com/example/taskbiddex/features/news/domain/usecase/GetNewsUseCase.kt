package com.example.taskbiddex.features.news.domain.usecase

import com.example.taskbiddex.features.news.domain.repo.NewsRepoAbstract


class GetNewsUseCase (private val articlesRepo : NewsRepoAbstract) {
    suspend operator fun invoke(page : Int) = articlesRepo.getAllNews(page)
}