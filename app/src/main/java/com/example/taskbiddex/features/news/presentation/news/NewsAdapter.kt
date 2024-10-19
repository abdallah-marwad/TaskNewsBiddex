package com.example.taskbiddex.features.news.presentation.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taskbiddex.R
import com.example.taskbiddex.common.utils.DateFormatter
import com.example.taskbiddex.databinding.ItemArticleBinding
import com.example.taskbiddex.features.news.data.model.article.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemArticleBinding = ItemArticleBinding.bind(itemView)
    }

    private val differCallBack =
        object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    val differ = AsyncListDiffer(this, differCallBack)


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
        return getListCount()
    }

    private fun getListCount(): Int {

       return differ.currentList.size

    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        var article = differ.currentList[position]

        holder.binding.apply {
            Glide.with(root).load(article?.urlToImage)
                .placeholder(R.drawable.err_banner)
                .error(R.drawable.err_banner)
                .into(ivArticleImage)
            tvAuthor.text = article?.author
            title.text = article?.title
            tvDescription.text = article?.description
            tvPublishedAt.text = article?.publishedAt?.let { DateFormatter().formatDate(it) } ?: ""
        }
        holder.binding.root.setOnClickListener {
            onItemClickListener?.let {
                if (article != null) {
                    it(article)
                }
            }
        }
    }

    // Lambda For Listener
    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}