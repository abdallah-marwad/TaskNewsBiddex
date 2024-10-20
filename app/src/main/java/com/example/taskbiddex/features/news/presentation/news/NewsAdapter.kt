package com.example.taskbiddex.features.news.presentation.news

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taskbiddex.R
import com.example.taskbiddex.common.utils.DateFormatter
import com.example.taskbiddex.databinding.ItemArticleBinding
import com.example.taskbiddex.features.news.domain.entity.article.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemArticleBinding = ItemArticleBinding.bind(itemView)
    }
    private val articleList = mutableListOf<Article>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        Log.d("test" , "position = $position")
        var article = articleList[position]
        holder.binding.apply {
            Glide.with(root).load(article.urlToImage)
                .placeholder(R.drawable.err_banner)
                .override(360 , 360)
                .error(R.drawable.err_banner)
                .dontAnimate()
                .into(ivArticleImage)
            tvAuthor.text = article.author
            title.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt?.let { DateFormatter().formatDate(it) } ?: ""
        }
        holder.binding.root.setOnClickListener {
            onItemClickListener?.let {
                if (article != null) {
                    it(article , holder.binding.ivArticleImage)
                }
            }
        }
    }
    fun submitPaginatedData(newArticles: List<Article>) {
        articleList.addAll(newArticles)
        notifyDataSetChanged()
    }
    fun clearData() {
        articleList.clear()
    }
    // Lambda For Listener
    private var onItemClickListener: ((Article, ImageView) -> Unit)? = null
    fun setOnClickListener(listener: (Article, ImageView) -> Unit) {
        onItemClickListener = listener
    }

}