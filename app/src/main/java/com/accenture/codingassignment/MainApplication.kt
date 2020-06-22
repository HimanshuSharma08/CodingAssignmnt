package com.accenture.codingassignment

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager

class MainApplication : Application() {

    // Singleton object
    companion object {
        lateinit var instance: MainApplication // Application instance
        //The base URL of the API
        // We can also place this in Cofig File or seprate Constant file
        const val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"


        val isConnected: Boolean // This gives network status whether Internet is available or not
            get() {
                val connectivityManager =
                    instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val netInfo = connectivityManager.activeNetworkInfo
                return netInfo != null && netInfo.isConnected
            }
    }



    override fun onCreate() {
        super.onCreate()
        instance = this // initialization
    }
}