package com.example.taskbiddex.features.news.presentation.news

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
import com.example.taskbiddex.features.news.domain.entity.article.Article
import com.example.taskbiddex.features.news.domain.entity.article.NewsResponse
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
        activityOnClick()
    }

    private fun activityOnClick() {
        binding.swipView.setOnRefreshListener {
           swipeToRefreshAction()
        }
        newsItemClick()
    }

    private fun swipeToRefreshAction() {
        showShimmer()
        viewModel.getAllNews(pageNum = 1)
        binding.swipView.isRefreshing = false
    }

    override fun getViewModelClass(): Class<NewsViewModel> {
        return NewsViewModel::class.java
    }

    // region init views
    private fun initViews() {
        binding.appBar.appbarImgBack.visibility = View.GONE
        binding.rvBreakingNews.adapter = newsAdapter
        binding.rvBreakingNews.addOnScrollListener(recyclerPaging)
    }
    private fun newsItemClick() {
        newsAdapter.setOnClickListener { article, image ->
            startNewsDetailsScreenWithSharedImg(article , image)
        }
    }

    private fun startNewsDetailsScreenWithSharedImg(article: Article, image: ImageView) {
        val intent = Intent(this , NewsDetailsActivity::class.java)
        intent.putExtra(Constant.ARTICLE, article)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, image, getString(R.string.shared_img)
        )
        startActivity(intent, options.toBundle())
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
        hideProgress()
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
        binding.errArea.errArea.visibility = View.VISIBLE
        binding.errArea.errTxt.text = getString(resourceMsg)
        binding.errArea.imgErr.setImageResource(resourceDrawable)
    }

    override fun noInternetCallBack() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noInternet.collect {
                    hideProgress()
                    handleErr(
                        getString(R.string.check_internet_connection),
                        R.string.check_internet_connection,
                        R.drawable.no_internet
                    )
                }
            }
        }
    }

    private fun hideProgress() {
        binding.pagingProgress.visibility = View.GONE
        hideShimmer()
    }

    private fun handleAllNewsLoading() {
        if (recyclerPaging.isPaginate)
            binding.pagingProgress.visibility = View.VISIBLE
        else
            showShimmer()
    }
    private fun showShimmer() {
        binding.shimmer.shimmer.visibility = View.VISIBLE
        binding.shimmer.shimmer.startShimmer()
        binding.rvBreakingNews.visibility = View.GONE

    }
    private fun hideShimmer() {
        binding.shimmer.shimmer.stopShimmer()
        binding.shimmer.shimmer.visibility = View.GONE
        binding.rvBreakingNews.visibility = View.VISIBLE

    }

    private fun handleSuccessGetNews(response: NewsResponse) {
        recyclerPaging.isLoading = false
        hideProgress()
        binding.errArea.errArea.visibility = View.GONE
        submitDataToAdapter(response)
        handleLastPage(response.totalResults)
    }

    private fun submitDataToAdapter(response: NewsResponse) {
        // check for (viewModel.currentPage ==2) because it meaning
        // that is first page and it may be come from
        // swipe to refresh and the recycler could be contain data.
        if(viewModel.currentPage ==2) newsAdapter.clearData()
        newsAdapter.submitPaginatedData(response.articles)
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