package me.androidbox.pokemon.presentation.utils

import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.MutableLiveData

class NetworkConnectivity : ConnectivityManager.NetworkCallback() {

    val networkStatus = MutableLiveData<String>()

    override fun onLost(network: Network) {
        super.onLost(network)
        networkStatus.value = "onLost"
    }

    override fun onUnavailable() {
        super.onUnavailable()
        networkStatus.value = "onUnavailable"
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        super.onLosing(network, maxMsToLive)
        networkStatus.value = "onLosing"
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        networkStatus.value = "onAvailable"
    }
}
