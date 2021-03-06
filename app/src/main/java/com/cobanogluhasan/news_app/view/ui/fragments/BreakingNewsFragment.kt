package com.cobanogluhasan.news_app.view.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cobanogluhasan.news_app.data.viewmodel.NewsViewModel
import com.cobanogluhasan.news_app.databinding.FragmentBreakingNewsBinding
import com.cobanogluhasan.news_app.util.Constants
import com.cobanogluhasan.news_app.util.Constants.Companion.QUERY_PAGE_SIZE
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
            val action =
                BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticlesFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun observeLiveData() {
        viewModel.breakingNewsData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages
                        if (isLastPage) binding.rvBreakingNews.setPadding(0, 0, 0, 0)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.d(TAG, "onViewCreated: $message")
                        Toast.makeText(
                            requireContext(),
                            "An Error Occured! $message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Resource.Loading -> showProgressBar()
            }
        })
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.hide()
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.show()
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            //check if its scrolling
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getBreakingNews("us")
                isScrolling = false
            }
        }
    }

    private fun initRecycler() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}