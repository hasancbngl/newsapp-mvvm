package com.cobanogluhasan.news_app.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobanogluhasan.news_app.data.viewmodel.NewsViewModel
import com.cobanogluhasan.news_app.databinding.FragmentSavedNewsBinding
import com.cobanogluhasan.news_app.view.adapters.NewsAdapter

class SavedNewsFragment() : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding
    private val viewModel : NewsViewModel by activityViewModels()
    private lateinit var newsAdapter : NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        newsAdapter.setOnItemClickListener {
            val action = SavedNewsFragmentDirections.actionSavedNewsFragmentToArticlesFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun initRecycler() {
        newsAdapter = NewsAdapter()
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}