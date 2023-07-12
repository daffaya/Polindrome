package com.kumaa.palindrome.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kumaa.palindrome.R

fun ImageView.setImageFromUrl(context: Context, url: String) {
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.ic_image_24)
        .error(R.drawable.ic_broken_image_24)
        .into(this)
}