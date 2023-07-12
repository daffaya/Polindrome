package com.kumaa.palindrome.ui.userList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.kumaa.palindrome.data.repository.UserRepository
import com.kumaa.palindrome.data.response.UserItem
import com.kumaa.palindrome.utils.LoadingState
import kotlinx.coroutines.launch

class UserListViewModel(private val repo: UserRepository): ViewModel() {
    private val _userList = MutableLiveData<PagingData<UserItem>>()
    val userList: LiveData<PagingData<UserItem>> = _userList

    private val _loadingState = MutableLiveData<LoadingState<Unit>>()
    val loadingState: LiveData<LoadingState<Unit>> = _loadingState

    private var currentPage = 1
    private var isFetchingData = false

    init {
        fetchUserList()
    }

    fun fetchUserList() {
        if (isFetchingData) return

        viewModelScope.launch {
            _loadingState.value = LoadingState.Loading
            try {
                val users = repo.getAllUsers(currentPage, PER_PAGE)
                val pagingData = PagingData.from(users.data)
                _userList.value = pagingData
                currentPage++
                _loadingState.value = LoadingState.Success(Unit)
            } catch (e: Exception) {
                _loadingState.value = LoadingState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun refreshUserList() {
        if (isFetchingData) return

        currentPage = 1
        fetchUserList()
    }

    companion object {
        const val PER_PAGE = 10
    }
}