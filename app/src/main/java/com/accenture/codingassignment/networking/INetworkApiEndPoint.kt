package com.accenture.codingassignment.networking

import com.accenture.codingassignment.model.MainActivityDataModel
import retrofit2.http.GET

import retrofit2.Call

//The base URL of the API
const val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"

internal interface INetworkApiEndPoint {

    @GET("facts.json")
    fun getListData(): Call<MainActivityDataModel>

}