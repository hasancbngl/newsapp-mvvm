package com.cobanogluhasan.news_app.view.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cobanogluhasan.news_app.data.viewmodel.NewsViewModel
import com.cobanogluhasan.news_app.databinding.FragmentBreakingNewsBinding
import com.cobanogluhasan.news_app.util.Resource
import com.cobanogluhasan.news_app.util.hide
import com.cobanogluhasan.news_app.util.show
import com.cobanogluhasan.news_app.view.adapters.NewsAdapter

class BreakingNewsFragment() : Fragment() {

    private lateinit var binding: FragmentBreakingNewsBinding
    private val viewModel: NewsViewModel by activityViewModels()
    private lateinit var newsAdapter: NewsAdapter
    private val TAG = "BreakingNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        observeLiveData()

        newsAdapter.setOnItemClickListener {
            Log.i(TAG, "onViewCreated: ")
            val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticlesFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun observeLiveData() {
        viewModel.breakingNewsData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d(TAG, "onViewCreated: $message")
                    }
                }
                is Resource.Loading -> showProgressBar()
            }
        })
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.hide()
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.show()
    }

    private fun initRecycler() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}