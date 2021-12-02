package me.androidbox.pokemon.utils

import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkConnectivityFactory {
    fun wifiRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
            .addCapability(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }
}