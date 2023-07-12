package com.kumaa.palindrome.utils

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kumaa.palindrome.data.repository.UserRepository
import com.kumaa.palindrome.data.response.UserItem

@ExperimentalPagingApi
class UserPagingSource(private val repo: UserRepository) : PagingSource<Int, UserItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserItem> {
        val page = params.key ?: 1
        val perPage = params.loadSize
        return try {
            val users = repo.getAllUsers(page, perPage)
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (users.data.isNotEmpty()) page + 1 else null
            LoadResult.Page(data = users.data, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

