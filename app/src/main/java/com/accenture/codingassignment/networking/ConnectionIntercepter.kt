package com.accenture.codingassignment.networking

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import com.accenture.codingassignment.MainApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectionIntercepter(private val mContext: Context) : Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!MainApplication.isConnected) {
            throw NetworkException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }


}