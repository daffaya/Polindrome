package com.kumaa.palindrome.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.kumaa.palindrome.data.remote.ApiConfig
import com.kumaa.palindrome.data.repository.UserRepository
import com.kumaa.palindrome.databinding.ActivityHomeBinding
import com.kumaa.palindrome.ui.userList.UserListActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var repo: UserRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupViewModel()
        setupAction()
        setupBackButton()
    }

    private fun setupViewModel(){
        val apiService = ApiConfig.getApiService()
        repo = UserRepository(apiService)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(repo))[HomeViewModel::class.java]

    }

    private fun setupAction(){
        val name = intent.getStringExtra(EXTRA_NAME)
        if (name != null) {
            viewModel.setUserName(name)
        }

        binding.btnSelect.setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)
            startActivity(intent)
        }

        viewModel.showName.observe(this) { showName ->
            binding.tvUserName.text = showName.toString()
        }

        viewModel.selectedUserName.observe(this) { selectedUserName ->
            binding.tvSelectedUser.text = selectedUserName.toString()
        }
    }

    private fun setupBackButton(){
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }
    companion object {
        const val EXTRA_NAME = "extra_name"
    }
}