package com.kumaa.palindrome.ui.userList

import androidx.lifecycle.*
import androidx.paging.*
import com.kumaa.palindrome.data.repository.UserRepository
import com.kumaa.palindrome.data.response.UserItem
import com.kumaa.palindrome.utils.LoadingState
import com.kumaa.palindrome.utils.UserPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class UserListViewModel(private val repo: UserRepository) : ViewModel() {
    private lateinit var userListFlow: Flow<PagingData<UserItem>>
    val userList: LiveData<PagingData<UserItem>> by lazy {
        userListFlow.asLiveData()
    }

    private val _loadingState = MutableLiveData<LoadingState<Unit>>()
    val loadingState: LiveData<LoadingState<Unit>> = _loadingState

    init {
        fetchUserList()
    }

    fun fetchUserList() {
        viewModelScope.launch {
            try {
                val pager = Pager(
                    config = PagingConfig(
                        pageSize = PER_PAGE,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = { UserPagingSource(repo) }
                )
                userListFlow = pager.flow.cachedIn(viewModelScope)
                _loadingState.value = LoadingState.Success(Unit)
            } catch (e: Exception) {
                _loadingState.value = LoadingState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun refreshUserList() {
        (userList as? LiveData<PagingData<UserItem>>)?.value
        fetchUserList()
    }

    companion object {
        const val PER_PAGE = 3
    }
}