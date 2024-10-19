package com.example.taskbiddex.features.news.presentation.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.taskbiddex.R
import com.example.taskbiddex.common.core.BaseActivityMVVM
import com.example.taskbiddex.common.utils.Constant
import com.example.taskbiddex.common.utils.RecyclerPaging
import com.example.taskbiddex.common.utils.Resource
import com.example.taskbiddex.databinding.ActivityNewsBinding
import com.example.taskbiddex.features.news.data.model.article.NewsResponse
import com.example.taskbiddex.features.news.presentation.newsDetails.NewsDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsActivity : BaseActivityMVVM<ActivityNewsBinding, NewsViewModel>(),
    RecyclerPaging.ShouldPaginateCallBack {
    private val newsAdapter by lazy { NewsAdapter() }
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

    // region init views
    private fun initViews() {
        binding.appBar.appbarImgBack.visibility = View.GONE
        binding.rvBreakingNews.adapter = newsAdapter
        binding.rvBreakingNews.addOnScrollListener(recyclerPaging)
        newsItemClick()
    }
    private fun newsItemClick() {
        newsAdapter.setOnClickListener { article , image->
            val intent = Intent(this , NewsDetailsActivity::class.java)
            intent.putExtra(Constant.ARTICLE, article)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, image, "sharedImage"
            )
            startActivity(intent, options.toBundle())
        }
    }
    // endregion

    // region get all news
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

    private fun handleAllNewsFailure(message: String) {
        recyclerPaging.isLoading = false
        hideProgresses()
        handleErr(message)
    }

    private fun handleErr(
        message: String,
        resourceMsg: Int = R.string.some_err_happened,
        resourceDrawable: Int = R.drawable.paper_img,
    ) {
        if (viewModel.currentPage == 1)
            showErrDataArea(resourceMsg ,resourceDrawable)
        else
            showDialogWithMsg(message)
    }

    private fun showErrDataArea(
        resourceMsg: Int ,
        resourceDrawable: Int,

        ) {
        binding.errArea.visibility = View.VISIBLE
        binding.errTxt.text = getString(resourceMsg)
        binding.imgErr.setImageResource(resourceDrawable)
    }

    override fun noInternetCallBack() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noInternet.collect {
                    hideProgresses()
                    handleErr(
                        getString(R.string.check_internet_connection),
                        R.string.check_internet_connection,
                        R.drawable.no_internet
                    )
                }
            }
        }
    }

    private fun hideProgresses() {
        binding.pagingProgress.visibility = View.GONE
        hideProgressDialog()
    }

    private fun handleAllNewsLoading() {
        if (recyclerPaging.isPaginate)
            binding.pagingProgress.visibility = View.VISIBLE
        else
            showProgressDialog()
    }

    private fun handleSuccessGetNews(response: NewsResponse) {
        recyclerPaging.isLoading = false
        hideProgresses()
        binding.errArea.visibility = View.GONE
        newsAdapter.submitPaginatedData(response.articles)
        handleLastPage(response.totalResults)
    }

    // endregion

    // region pagination
    private fun handleLastPage(totalResults: Int) {
        if (totalResults == newsAdapter.itemCount)
            recyclerPaging.isLastPage = true
    }
    override fun shouldPaginateCallBack() {
        viewModel.getAllNews()
    }
   // endregion
}