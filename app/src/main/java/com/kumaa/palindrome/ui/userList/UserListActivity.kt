package com.kumaa.palindrome.ui.userList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kumaa.palindrome.R
import com.kumaa.palindrome.data.remote.ApiConfig
import com.kumaa.palindrome.data.repository.UserRepository
import com.kumaa.palindrome.databinding.ActivityUserListBinding
import com.kumaa.palindrome.ui.home.HomeActivity
import com.kumaa.palindrome.utils.LoadingState
@ExperimentalPagingApi
class UserListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserListBinding
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

        setupViewModel()
        setupUserList()

        swipeRefresh()
        setupBackButton()
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter() {
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("name", "${it.firstName} ${it.lastName}")
            }
            setResult(200, intent)
            finish()
        }
        recyclerView = binding.rvStory
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, UserListViewModelFactory(repo))[UserListViewModel::class.java]

        viewModel.userList.observe(this) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }

        viewModel.loadingState.observe(this) { loadingState ->
            when (loadingState) {
                is LoadingState.Loading -> {

                }
                is LoadingState.Success -> {

                }
                is LoadingState.Error -> {
                    showSnackBar(getString(R.string.empty_data))
                }
            }
        }
    }

    private fun setupUserList() {
        viewModel.fetchUserList()
    }

    private fun showEmptyState(show: Boolean) {
        if (show) {
            binding.tvNotFoundError.visibility = View.VISIBLE
            binding.ivNotFoundError.visibility = View.VISIBLE
        } else {
            binding.tvNotFoundError.visibility = View.GONE
            binding.ivNotFoundError.visibility = View.GONE
        }
    }
    private fun setupBackButton() {
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            adapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}