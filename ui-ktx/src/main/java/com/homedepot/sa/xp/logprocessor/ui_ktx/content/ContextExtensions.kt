package com.homedepot.sa.xp.logprocessor.ui_ktx.content

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isInternetAvailable(): Boolean {
    val connectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connectivityManager?.let {
        it.getNetworkCapabilities(it.activeNetwork)?.apply {
            if (hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED
                )
            ) {
                return when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
    }
    return false
}