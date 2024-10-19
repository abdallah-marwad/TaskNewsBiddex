package com.example.taskbiddex.features.news.presentation.news

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.taskbiddex.common.core.BaseActivity
import com.example.taskbiddex.common.core.BaseActivityMVVM
import com.example.taskbiddex.common.utils.Resource
import com.example.taskbiddex.databinding.ActivityNewsBinding
import com.example.taskbiddex.features.news.data.model.article.Article
import com.example.taskbiddex.features.news.data.model.article.NewsResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsActivity : BaseActivityMVVM<ActivityNewsBinding , NewsViewModel>() {
    private val newsAdapter by lazy {  NewsAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        viewModel.getAllNews()
        getAllNewsCallBack()
    }

    override fun getViewModelClass(): Class<NewsViewModel> {
        return NewsViewModel::class.java
    }

    private fun initViews() {
        binding.appBar.appbarImgBack.visibility = View.GONE
    }
    private fun getAllNewsCallBack() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsFlow.collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            handleSuccessGetNews(result.data!!)
                        }

                        is Resource.Failure -> {
                            hideProgressDialog()
                            showDialogWithMsg(result.message!!.message!!)
                        }

                        is Resource.Loading -> {
                            showProgressDialog()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun handleSuccessGetNews(data: MutableList<Article>) {
        hideProgressDialog()
        newsAdapter.differ.submitList(data)
        binding.rvBreakingNews.adapter = newsAdapter
    }
}