package com.example.taskbiddex.features.news.presentation.news

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.taskbiddex.common.core.BaseActivity
import com.example.taskbiddex.common.core.BaseActivityMVVM
import com.example.taskbiddex.common.utils.RecyclerPaging
import com.example.taskbiddex.common.utils.Resource
import com.example.taskbiddex.databinding.ActivityNewsBinding
import com.example.taskbiddex.features.news.data.model.article.Article
import com.example.taskbiddex.features.news.data.model.article.NewsResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsActivity : BaseActivityMVVM<ActivityNewsBinding , NewsViewModel>() ,
    RecyclerPaging.ShouldPaginateCallBack{
    private val newsAdapter by lazy {  NewsAdapter() }
    private val recyclerPaging by lazy { RecyclerPaging(this) }

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
        binding.rvBreakingNews.adapter = newsAdapter
        binding.rvBreakingNews.addOnScrollListener(recyclerPaging)
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
                           handleAllNewsFailure(result.message!!.message!!)
                        }

                        is Resource.Loading -> {
                            handleAllNewsLoading()
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun handleAllNewsFailure(message : String) {
        recyclerPaging.isLoading = false
        binding.pagingProgress.visibility = View.GONE
        hideProgressDialog()
        showDialogWithMsg(message)
    }

    private fun handleAllNewsLoading() {
        if (recyclerPaging.isPaginate)
            binding.pagingProgress.visibility = View.VISIBLE
        else
            showProgressDialog()
    }

    private fun handleSuccessGetNews(response: NewsResponse) {
        recyclerPaging.isLoading = false
        hideProgressDialog()
        binding.pagingProgress.visibility = View.GONE
        newsAdapter.submitPaginatedData(response.articles)
        handleLastPage(response.totalResults)
    }

    private fun handleLastPage(totalResults: Int) {
        if(totalResults == newsAdapter.itemCount)
            recyclerPaging.isLastPage = true
    }

    override fun shouldPaginateCallBack() {
        viewModel.getAllNews()
    }
}