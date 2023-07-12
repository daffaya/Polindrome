package com.kumaa.palindrome.ui.userList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kumaa.palindrome.data.repository.UserRepository

class UserListViewModelFactory(private val repo: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
