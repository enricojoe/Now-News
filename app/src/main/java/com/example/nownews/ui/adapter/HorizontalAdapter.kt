package com.example.nownews.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nownews.R
import com.example.nownews.databinding.HeadlineCardviewDesignBinding
import com.example.nownews.model.Article
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class HorizontalAdapter(private var headline: List<Article>) : RecyclerView.Adapter<HorizontalAdapter.HeadlineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val binding = HeadlineCardviewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeadlineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        holder.bind(headline[position])
    }

    override fun getItemCount(): Int = headline.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Article>) {
        headline = newData
        notifyDataSetChanged()
    }

    class HeadlineViewHolder(private val binding: HeadlineCardviewDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(headline: Article) {
            Glide.with(itemView)
                .load(headline.urlToImage)
                .placeholder(R.drawable.no_image_placeholder)
                .error(R.drawable.no_image_placeholder)
                .into(binding.headlineImage)
            binding.newsTitle.text = headline.title
            binding.newsSource.text = headline.source?.name
            val parser: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val formatter: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
            val output: String = parser.parse(headline.publishedAt)
                ?.let { formatter.format(it) }.toString()
            binding.newsDate.text = output
        }
    }
}