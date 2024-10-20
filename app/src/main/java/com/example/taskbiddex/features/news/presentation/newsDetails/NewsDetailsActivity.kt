package com.example.taskbiddex.features.news.presentation.newsDetails

import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.taskbiddex.R
import com.example.taskbiddex.common.core.BaseActivity
import com.example.taskbiddex.common.utils.Constant
import com.example.taskbiddex.common.utils.DateFormatter
import com.example.taskbiddex.common.utils.UrlOpener
import com.example.taskbiddex.databinding.ActivityNewsDetailsBinding
import com.example.taskbiddex.features.news.domain.entity.article.Article

class NewsDetailsActivity : BaseActivity<ActivityNewsDetailsBinding>() {
    lateinit var article : Article
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        article = intent.getSerializableExtra(Constant.ARTICLE ) as Article
        initViews()
        activityOnClick()
    }

    private fun activityOnClick() {
        binding.appBar.appbarImgBack.setOnClickListener {
            finish()
        }
        binding.extendedFab.setOnClickListener {
            article.url?.let { UrlOpener(it , this) }
        }
    }

    private fun initViews() {
        binding.appBar.appbarTxt.text = article.source?.name ?: "Unknown"
        binding.articleTitle.text = article.title
        binding.articleDescription.text = article.description
        binding.sourceName.text = article.source?.name
        Glide.with(this)
            .load(article.urlToImage)
            .placeholder(R.drawable.err_banner)
            .error(R.drawable.err_banner)
            .into(binding.ivArticleImage)
        binding.publishedAtTxt.text = article.publishedAt?.let {
            DateFormatter().fullFormatDate(
                it
            )
        }
    }
}