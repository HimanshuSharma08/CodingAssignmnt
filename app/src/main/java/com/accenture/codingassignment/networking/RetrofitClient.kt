package com.accenture.codingassignment.networking

import android.content.Context
import com.accenture.codingassignment.MainApplication
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getRetrofitClient(mContext: Context): Retrofit? {
        if (retrofit == null) {

            val oktHttpClient = OkHttpClient.Builder()
                .addInterceptor(ConnectionIntercepter())

            retrofit = Retrofit.Builder()
                .baseUrl(MainApplication.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                // Adding our OkHttpClient
                .client(oktHttpClient.build())
                .build()

        }
        return retrofit
    }
}