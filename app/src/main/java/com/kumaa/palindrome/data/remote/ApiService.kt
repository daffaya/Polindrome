package com.kumaa.palindrome.data.remote

import com.kumaa.palindrome.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getAllUser(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UserResponse
}
