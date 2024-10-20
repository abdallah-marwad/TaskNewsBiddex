package com.example.taskbiddex.features.news.presentation.news

import androidx.lifecycle.viewModelScope
import com.example.taskbiddex.common.utils.Resource
import com.example.taskbiddex.features.news.domain.entity.article.NewsResponse
import com.example.taskbiddex.features.news.domain.usecase.GetNewsUseCase
import com.tailors.doctoria.application.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel@Inject constructor(
    private val newsUseCase: GetNewsUseCase
): BaseViewModel() {
    var currentPage = 1
    private val _newsFlow = Channel<Resource<NewsResponse?>>()
    val newsFlow by lazy { _newsFlow.receiveAsFlow() }

    fun getAllNews(pageNum : Int = currentPage) {
        viewModelScope.launch(Dispatchers.IO) {
            if(pageNum == 1) currentPage = 1
            _newsFlow.send(Resource.Loading())
            when (val response = newsUseCase.invoke(pageNum)) {
                is Resource.Failure<*> -> {
                    handleFailure(response.message, _newsFlow)
                }
                is Resource.Success -> {
                    _newsFlow.send(Resource.Success(response.data))
                    currentPage++
                }

                else -> {}
            }
        }
    }
}