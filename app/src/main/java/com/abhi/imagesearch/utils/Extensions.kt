package com.abhi.imagesearch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import com.abhi.imagesearch.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageUrl(url: String?, requestOptions: RequestOptions) {
    Glide.with(this.context)
        .load(url)
        .placeholder(R.drawable.image_placeholder)
        .apply(requestOptions)
        .into(this)
}
fun isNetworkStatusAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let { it ->
        if (android.os.Build.VERSION.SDK_INT > 23) {
            it.getNetworkCapabilities(it.activeNetwork)?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    return true
                }

            }
        } else {
            it.activeNetworkInfo?.let {
                return it.isConnectedOrConnecting
            }

        }
    }
    return false
}

