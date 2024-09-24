package com.rmg.production_monitor.core.managers.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.rmg.production_monitor.core.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class NetworkManagerImpl  @Inject constructor(
    @ApplicationContext private val context: Context
)  : NetworkManager {

    override fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
//        return false
    }

    override fun getNetworkType(): String {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return Constants.NetworkType.UNKNOWN.value
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return Constants.NetworkType.UNKNOWN.value
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> Constants.NetworkType.WIFI.value
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> Constants.NetworkType.CELLULAR.value
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> Constants.NetworkType.ETHERNET.value
            else -> Constants.NetworkType.UNKNOWN.value
        }
//        return Constants.NetworkType.UNKNOWN.value
    }


}

