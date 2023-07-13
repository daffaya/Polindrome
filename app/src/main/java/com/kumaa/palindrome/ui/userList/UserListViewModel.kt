package com.kumaa.palindrome.ui.userList

import androidx.lifecycle.*
import androidx.paging.*
import com.kumaa.palindrome.data.repository.UserRepository
import com.kumaa.palindrome.data.response.UserItem
import com.kumaa.palindrome.utils.LoadingState
import com.kumaa.palindrome.utils.UserPagingSource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserListViewModel(private val repo: UserRepository) : ViewModel() {
    private var userListFlow: Flow<PagingData<UserItem>> = flowOf(PagingData.empty())
    val userList: LiveData<PagingData<UserItem>> by lazy {
        userListFlow.asLiveData()
    }

    private val _loadingState = MutableLiveData<LoadingState<Unit>>()
    val loadingState: LiveData<LoadingState<Unit>> = _loadingState

    init {
        fetchUserList()
    }

    @OptIn(ExperimentalPagingApi::class)
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

    companion object {
        const val PER_PAGE = 5
    }
}