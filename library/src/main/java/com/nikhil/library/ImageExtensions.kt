package com.nikhil.library

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String, @DrawableRes placeholder: Int = R.drawable.back ){
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}