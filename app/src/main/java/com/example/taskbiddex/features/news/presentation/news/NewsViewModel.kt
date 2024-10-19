package com.example.taskbiddex.features.news.presentation.news

import androidx.lifecycle.viewModelScope
import com.example.taskbiddex.common.utils.Resource
import com.example.taskbiddex.features.news.data.model.article.Article
import com.example.taskbiddex.features.news.data.model.article.NewsResponse
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
    private val _newsFlow = Channel<Resource<MutableList<Article>>>()
    val newsFlow by lazy { _newsFlow.receiveAsFlow() }

    fun getAllNews(pageNum: Int = 1) {
        viewModelScope.launch(Dispatchers.IO) {
            _newsFlow.send(Resource.Loading())
            when (val signInStatus = newsUseCase.invoke(pageNum)) {
                is Resource.Failure<*> -> {
                    handleFailure(signInStatus.message, _newsFlow)
                }
                is Resource.Success<*> -> {
                    _newsFlow.send(Resource.Success(signInStatus.data))
                }

                else -> {}
            }
        }
    }
}