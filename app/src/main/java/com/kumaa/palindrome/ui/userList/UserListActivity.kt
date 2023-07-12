package com.kumaa.palindrome.ui.userList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kumaa.palindrome.data.remote.ApiConfig
import com.kumaa.palindrome.data.repository.UserRepository
import com.kumaa.palindrome.databinding.ActivityUserListBinding
import com.kumaa.palindrome.utils.LoadingState

class UserListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserListBinding
    private lateinit var viewModel: UserListViewModel
    private lateinit var adapter: UserListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var repo: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val apiService = ApiConfig.getApiService()
        repo = UserRepository(apiService)
        setupRecyclerView()
        swipeRefresh()

        setupViewModel()
        setupUserList()

        setupBackButton()
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter()
        recyclerView = binding.rvStory
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, UserListViewModelFactory(repo))[UserListViewModel::class.java]

        viewModel.userList.observe(this) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }

        viewModel.loadingState.observe(this) { loadingState ->
            when (loadingState) {
                is LoadingState.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                is LoadingState.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                }
                is LoadingState.Error -> {
                    binding.swipeRefresh.isRefreshing = false
                    // Show error message or handle the error state
                }
            }
        }
    }

    private fun setupUserList(){
        viewModel.fetchUserList()
    }

    private fun setupBackButton(){
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshUserList()
        }
    }
}