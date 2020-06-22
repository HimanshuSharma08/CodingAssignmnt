package com.accenture.codingassignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.accenture.codingassignment.MainApplication
import com.accenture.codingassignment.model.MainActivityDataModel
import com.accenture.codingassignment.networking.INetworkApiEndPoint
import com.accenture.codingassignment.networking.NetworkException
import com.accenture.codingassignment.networking.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException


// This is used to perform network/DB related work like send request and response
class MainActivityRepository {

    val TAG = MainActivityRepository::class.java.simpleName

    // data holder of MainActivityDataModel type, which contains api response
    val listData = MutableLiveData<MainActivityDataModel>()

    // MainApplication reference
    var mMainApplication: MainApplication

    // Boolean value to notify whether response is from Server or not.
    var isDataFromApi = MutableLiveData<Boolean>()

    init {
        // get Application instance
        mMainApplication = MainApplication.instance
    }

    // This method is user to send network request and fetch response.
    fun fetchListData() {

        val retrofit = RetrofitClient.getRetrofitClient(mMainApplication)

        val service = retrofit?.create(INetworkApiEndPoint::class.java)

        service?.getListData()?.enqueue(object : Callback<MainActivityDataModel> {

            // show network error
            override fun onFailure(call: Call<MainActivityDataModel>, t: Throwable) {

                // handle error like Socket Timeout, UnknownHost, IOException
                // we can make listData as generic like ANY and then, check its instance
                if (t is NetworkException) {
                    Log.d(TAG, "Internet Issue")
                } else if (t is SocketTimeoutException) {
                    Log.d(
                        TAG,
                        "Api is taking more time than expected, So unabel to load complete data"
                    )
                }
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