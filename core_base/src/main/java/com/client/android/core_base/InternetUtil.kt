package com.client.core_base

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
fun Context.isInternetAvailableOnPhone(): Boolean {
    var result = false
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.run {
        connectivityManager.activeNetworkInfo?.run {
            result = when (type) {
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        }
    }
    return result
}
