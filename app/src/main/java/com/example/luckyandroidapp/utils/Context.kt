package com.example.luckyandroidapp.utils

import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun Context.dpToPx(dp: Int): Int = (dp * this.resources.displayMetrics.density).toInt()

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.isConnectedNetwork(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networks = cm.activeNetwork
    networks ?: return false
    val activeNetworks = cm.getNetworkCapabilities(networks) ?: return false
    return when {
        activeNetworks.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                activeNetworks.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) -> true
        else -> false
    }
}