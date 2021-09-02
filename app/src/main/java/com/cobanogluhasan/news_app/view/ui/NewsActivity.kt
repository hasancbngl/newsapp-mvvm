package com.cobanogluhasan.news_app.view.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cobanogluhasan.news_app.R
import com.cobanogluhasan.news_app.data.viewmodel.NewsViewModel
import com.cobanogluhasan.news_app.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //connect the menu with navigation view
        supportFragmentManager.findFragmentById(
            R.id.navHost
        )?.findNavController()?.let {
            binding.bottomNavigationView.setupWithNavController(it)
        }

        viewModel.getBreakingNews("tr")
    }
}