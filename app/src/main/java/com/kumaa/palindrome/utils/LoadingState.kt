package com.kumaa.palindrome.utils

sealed class LoadingState<out R> private constructor() {
    data class Success<out T>(val data: T) : LoadingState<T>()
    data class Error(val data: String) : LoadingState<Nothing>()
    object Loading : LoadingState<Nothing>()
}