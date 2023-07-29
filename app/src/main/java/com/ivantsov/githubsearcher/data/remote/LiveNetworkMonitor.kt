package com.ivantsov.githubsearcher.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


class LiveNetworkMonitor(private val applicationContext: Context) {
    val isConnected: Boolean
        get() = (applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getNetworkCapabilities(activeNetwork)?.run {
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    ) || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                } ?: false
            } else {
                val connectivityManager =
                    applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                return if (connectivityManager != null) {
                    val activeNetwork = connectivityManager.activeNetworkInfo
                    activeNetwork != null && activeNetwork.isConnectedOrConnecting
                } else {
                    false
                }
            }
        }

}