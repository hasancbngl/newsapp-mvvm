package com.cobanogluhasan.news_app.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cobanogluhasan.news_app.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}