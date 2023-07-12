package com.kumaa.palindrome.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

    @field:SerializedName("per_page")
	val perPage: Int,

    @field:SerializedName("total")
	val total: Int,

    @field:SerializedName("data")
	val data: List<UserItem>,

    @field:SerializedName("page")
	val page: Int,

    @field:SerializedName("total_pages")
	val totalPages: Int,

    @field:SerializedName("support")
	val support: Support
) : Parcelable