package com.cobanogluhasan.news_app.view.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cobanogluhasan.news_app.R
import com.cobanogluhasan.news_app.data.db.ArticleDatabase
import com.cobanogluhasan.news_app.data.repository.NewsRepository
import com.cobanogluhasan.news_app.data.viewmodel.NewsViewModel
import com.cobanogluhasan.news_app.data.viewmodel.NewsViewModelProviderFactory
import com.cobanogluhasan.news_app.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        viewModel.getBreakingNews("us")

        //connect the menu with navigation view
        supportFragmentManager.findFragmentById(
            R.id.navHost
        )?.findNavController()?.let {
            binding.bottomNavigationView.setupWithNavController(it)
        }
    }
}