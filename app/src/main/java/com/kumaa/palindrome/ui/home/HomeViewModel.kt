package com.kumaa.palindrome.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {
    private val _showName = MutableLiveData<String>()
    val showName: LiveData<String> = _showName

    fun setUserName(name: String) {
        _showName.value = name
    }
}