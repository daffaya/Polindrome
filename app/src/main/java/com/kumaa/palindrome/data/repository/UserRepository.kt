package com.kumaa.palindrome.data.repository

import com.kumaa.palindrome.data.remote.ApiService
import com.kumaa.palindrome.data.response.UserResponse

class UserRepository(private val apiService: ApiService) {
    suspend fun getAllUsers(page: Int, perPage: Int): UserResponse {
        return apiService.getAllUser(page, perPage)
    }
}