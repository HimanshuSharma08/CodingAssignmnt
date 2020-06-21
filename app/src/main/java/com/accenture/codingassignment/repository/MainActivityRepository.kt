package com.accenture.codingassignment.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.accenture.codingassignment.MainApplication
import com.accenture.codingassignment.model.MainActivityDataModel
import com.accenture.codingassignment.networking.INetworkApiEndPoint
import com.accenture.codingassignment.networking.NetworkException
import com.accenture.codingassignment.networking.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityRepository {
    val listData = MutableLiveData<MainActivityDataModel>()
    var mMainApplication: MainApplication
    var isDataFromApi = MutableLiveData<Boolean>()

    init {
        mMainApplication = MainApplication.instance
    }

    fun fetchListData() {

        val retrofit = RetrofitClient.getRetrofitClient(mMainApplication)

        val service = retrofit?.create(INetworkApiEndPoint::class.java)

        service?.getListData()?.enqueue(object : Callback<MainActivityDataModel> {

            // show network error
            override fun onFailure(call: Call<MainActivityDataModel>, t: Throwable) {

                // handle error like Socket Timeout, UnknownHost, IOException
                // we can make listData as generic like ANY and then, check its instance
            }

            override fun onResponse(
                call: Call<MainActivityDataModel>,
                response: Response<MainActivityDataModel>
            ) {
                if (response.body() != null)
                    isDataFromApi.value = true;
                    listData.value = response.body()
            }
        })
    }
}