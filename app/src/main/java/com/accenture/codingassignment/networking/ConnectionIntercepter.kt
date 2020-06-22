package com.accenture.codingassignment.networking

import com.accenture.codingassignment.MainApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectionIntercepter() : Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!MainApplication.isConnected) {
            throw NetworkException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }


}