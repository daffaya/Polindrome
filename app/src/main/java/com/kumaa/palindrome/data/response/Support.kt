package com.kumaa.palindrome.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Support(

    @field:SerializedName("text")
    val text: String,

    @field:SerializedName("url")
    val url: String
) : Parcelable