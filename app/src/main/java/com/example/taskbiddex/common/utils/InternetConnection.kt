package com.tailors.doctoria.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.taskbiddex.MyApplication

class InternetConnection {
    fun hasInternetConnection(): Boolean {
        val connectivityManager = MyApplication.myAppContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capability =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capability.hasTransport(TRANSPORT_WIFI) -> true
            capability.hasTransport(TRANSPORT_CELLULAR) -> true
            capability.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
