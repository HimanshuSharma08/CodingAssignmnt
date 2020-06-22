package com.accenture.codingassignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.accenture.codingassignment.model.MainActivityDataModel
import com.accenture.codingassignment.repository.MainActivityRepository


// This class is used as create loose coupling between activities and Repositories
// i.e. MainActivity and MainActivityRepository. It takes request from View class and pass it to repo
// class where network call code is written. When we get response from server, it returns back that required
// reference to view.
class MainActivityViewModel : ViewModel() {
    // Repositry class reference
    // where we write network related code to make request and get response from server
    private val repository = MainActivityRepository()

    // livedata reference, which holds list of MainActivityDataModel
    val listData: LiveData<MainActivityDataModel>

    // Check to notify whether response is from API or not
    val isDataFromApi: LiveData<Boolean>


    // This is first to be executed when class is instantiated
    init {
        this.listData = repository.listData // initialize it with passing
        this.isDataFromApi = repository.isDataFromApi
    }

    // Method to fetch APi response from repository
    fun fetchListData() {
        repository.fetchListData()
    }

    //every time observer gets call, it returns previous value first.
    //so to get updated Value, We are using this
    fun updateValue() {
        repository.isDataFromApi.value = false
    }
}