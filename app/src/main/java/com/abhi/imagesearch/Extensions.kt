package com.abhi.imagesearch

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageUrl(url: String?, requestOptions: RequestOptions) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.image_placeholder)
        .apply(requestOptions)
        .into(this)
}

