package com.kumaa.palindrome.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kumaa.palindrome.data.repository.UserRepository
import com.kumaa.palindrome.utils.LoadingState

class HomeViewModel(private val repo: UserRepository): ViewModel() {
    private val _showName = MutableLiveData<String>()
    val showName: LiveData<String> = _showName

    private val _selectedUserName = MutableLiveData<LoadingState<Unit>>()
    val selectedUserName: LiveData<LoadingState<Unit>> = _selectedUserName

    private val _loadingState = MutableLiveData<LoadingState<Unit>>()
    val loadingState: LiveData<LoadingState<Unit>> = _loadingState

    fun setUserName(name: String) {
        _showName.value = name
    }
}