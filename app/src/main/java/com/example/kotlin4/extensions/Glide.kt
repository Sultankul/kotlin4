package com.example.kotlin4.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadWithGlide(url: String) {
    Glide.with(this).load(url).centerCrop().into(this)
}