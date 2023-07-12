package com.kumaa.palindrome.ui.check

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheckViewModel: ViewModel() {
    private val _checkResult = MutableLiveData<Boolean>()
    val checkResult: LiveData<Boolean> = _checkResult

    fun performPalindromeCheck(sentence: String) {
        val isPalindrome = checkIfPalindrome(sentence)
        _checkResult.value = isPalindrome
    }

    private fun checkIfPalindrome(sentence: String): Boolean {
        val reverseString = sentence.reversed()
        return sentence.equals(reverseString, ignoreCase = true)
    }


}