package com.cobanogluhasan.news_app.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.cobanogluhasan.model.Article
import com.cobanogluhasan.news_app.databinding.ItemArticlePreviewBinding
import android.graphics.drawable.Drawable
import androidx.annotation.Nullable
import com.bumptech.glide.load.DataSource

import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.target.Target


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val TAG = "NewsAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemArticlePreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
           binding.apply {
               Log.i(TAG, "bind: ${article.url}")
               Glide.with(ivArticleImage.context).load(article.urlToImage)
                   .into(ivArticleImage)

                tvSource.text = article.source.name
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt

                binding.root.setOnClickListener {
                    onItemClickListener?.let { it(article) }
                }

            }
        }
    }

    //update only changed list
    private val differCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}