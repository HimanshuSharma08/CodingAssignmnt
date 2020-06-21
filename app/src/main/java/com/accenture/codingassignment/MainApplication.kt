package com.accenture.codingassignment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
        val isConnected: Boolean
            get() {
                val connectivityManager =
                    instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val netInfo = connectivityManager.activeNetworkInfo
                return netInfo != null && netInfo.isConnected
            }
    }



    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}