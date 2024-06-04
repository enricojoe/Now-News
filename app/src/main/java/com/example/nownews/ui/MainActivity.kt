package com.example.nownews.ui

import androidx.activity.viewModels
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nownews.databinding.ActivityMainBinding
import com.example.nownews.model.Article
import com.example.nownews.network.RetrofitInstance
import com.example.nownews.repository.NewsRepository
import com.example.nownews.ui.adapter.HorizontalAdapter
import com.example.nownews.ui.adapter.VerticalAdapter
import com.example.nownews.ui.util.NewsViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(NewsRepository(RetrofitInstance.api))
    }

    private lateinit var horizontalAdapter: HorizontalAdapter
    private lateinit var verticalAdapter: VerticalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the horizontal RecyclerView
        val horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvHeadline.layoutManager = horizontalLayoutManager
        horizontalAdapter = HorizontalAdapter(emptyList()) // Initialize with empty list
        binding.rvHeadline.adapter = horizontalAdapter

        // Set up the vertical RecyclerView
        val verticalLayoutManager = LinearLayoutManager(this)
        binding.rvNews.layoutManager = verticalLayoutManager
        verticalAdapter = VerticalAdapter(emptyList()) // Initialize with empty list
        binding.rvNews.adapter = verticalAdapter

        // Observe the data from the ViewModel
        viewModel.combinedNewsLiveData.observe(this, Observer { pair ->
            pair?.let { it ->
                val newsArticles = it.first?.articles ?: emptyList()
                val headlines = it.second?.articles ?: emptyList()

                val nonNullNews = newsArticles
                    .filterNotNull()
                    .filterNot { it.title?.contains("[Removed]") == true }
                    .take(5 )
                val nonNullHeadline = headlines
                    .filterNotNull()
                    .filterNot { it.title?.contains("[Removed]") == true }
                    .take(20)

                // Update adapters with data
                val articleList = nonNullNews.map { article ->
                    Article(article.author,
                        article.content,
                        article.description,
                        article.publishedAt,
                        article.source,
                        article.title,
                        article.url,
                        article.urlToImage)
                }
                val headlineList = nonNullHeadline.map { headline ->
                    Article(headline.author,
                        headline.content,
                        headline.description,
                        headline.publishedAt,
                        headline.source,
                        headline.title,
                        headline.url,
                        headline.urlToImage)
                }

                horizontalAdapter.updateData(articleList)
                verticalAdapter.updateData(headlineList)
            }
        })
    }
}