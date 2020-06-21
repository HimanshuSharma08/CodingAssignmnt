package com.accenture.codingassignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.accenture.codingassignment.MainApplication
import com.accenture.codingassignment.R
import com.accenture.codingassignment.adapter.MainActivityRecyclerAdapter
import com.accenture.codingassignment.databinding.ActivityMainBinding
import com.accenture.codingassignment.model.MainActivityDataModel
import com.accenture.codingassignment.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mActivityMainBinding: ActivityMainBinding;
    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mAdapter: MainActivityRecyclerAdapter
    private lateinit var mResponseObserver: Observer<MainActivityDataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        initRef()
        setSwipeListener()
        setAdapter()
        getDataFromApi()
    }

    private fun setSwipeListener() {
        mActivityMainBinding.activityMainSwipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            mActivityMainBinding.activityMainSwipeRefresh.isRefreshing = false
            getDataFromApi()
        })
    }

    private fun getDataFromApi() {
        if (MainApplication.isConnected) {
            callApi()
            setObserver()
        } else
            showErrorMsg(getString(R.string.network_error))
    }

    private fun showLoadingBar(show: Boolean) {
        mActivityMainBinding.activityMainPb.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showErrorMsg(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setAdapter() {
        mAdapter = MainActivityRecyclerAdapter(this)
        mActivityMainBinding.activityMainRecyclerView.adapter = mAdapter
    }

    private fun initRef() {
        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mActivityMainBinding.activityMainRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun callApi() {
        mViewModel.fetchListData()
    }

    private fun setObserver() {
        showLoadingBar(true)
        if (mViewModel.listData.hasActiveObservers())
            mViewModel.listData.removeObservers(this)

        mResponseObserver = Observer {
            if (mViewModel.isDataFromApi.value == true) {
                setTitle(it.title)

                // update adapter
                mAdapter.setDataList(it.rows)
                showLoadingBar(false)
                if (mActivityMainBinding.activityMainSwipeRefresh.isRefreshing)
                    mActivityMainBinding.activityMainSwipeRefresh.isRefreshing = false

                mViewModel.updateValue()
            }
        }
        mViewModel.listData.observe(this, mResponseObserver)
    }

}
