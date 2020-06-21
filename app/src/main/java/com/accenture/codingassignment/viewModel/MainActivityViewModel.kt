package com.accenture.codingassignment.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.accenture.codingassignment.R
import com.accenture.codingassignment.model.MainActivityDataModel
import com.accenture.codingassignment.model.MainActivivityAdapterListModel
import com.accenture.codingassignment.repository.MainActivityRepository

class MainActivityViewModel : ViewModel() {
    private val repository = MainActivityRepository()
    val listData: LiveData<MainActivityDataModel>
    val isDataFromApi: LiveData<Boolean>

    init {
        this.listData = repository.listData
        this.isDataFromApi = repository.isDataFromApi
    }

    fun fetchListData() {
        repository.fetchListData()
    }

    // This is becoz, every time observer gets call, it returns previous value first. so to get updated Value, We use this
    fun updateValue() {
        repository.isDataFromApi.value = false
    }
}