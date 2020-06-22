package com.accenture.codingassignment.networking

import com.accenture.codingassignment.model.MainActivityDataModel
import retrofit2.http.GET
import retrofit2.Call

internal interface INetworkApiEndPoint {

    @GET("facts.json")
    fun getListData(): Call<MainActivityDataModel>

}