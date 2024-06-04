package com.example.nownews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nownews.R
import com.example.nownews.databinding.NewsCardviewDesignBinding
import com.example.nownews.model.Article
import java.text.SimpleDateFormat

class VerticalAdapter(private var news: List<Article>) : RecyclerView.Adapter<VerticalAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsCardviewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position])
    }

    override fun getItemCount(): Int = news.size

    fun updateData(newData: List<Article>) {
        news = newData
        notifyDataSetChanged()
    }

    class NewsViewHolder(private val binding: NewsCardviewDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: Article) {
            Glide.with(itemView)
                .load(news.urlToImage)
                .placeholder(R.drawable.no_image_placeholder)
                .error(R.drawable.no_image_placeholder)
                .into(binding.newsImage)
            binding.newsTitle.text = news.title
            binding.newsSource.text = news.source?.name
            binding.newsAuthor.text = news.author
            val parser: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val formatter: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
            val output: String = parser.parse(news.publishedAt)
                ?.let { formatter.format(it) }.toString()
            binding.newsDate.text = output
        }
    }
}