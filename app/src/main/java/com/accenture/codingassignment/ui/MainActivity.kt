package com.accenture.codingassignment.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.accenture.codingassignment.MainApplication
import com.accenture.codingassignment.R
import com.accenture.codingassignment.adapter.MainActivityRecyclerAdapter
import com.accenture.codingassignment.databinding.ActivityMainBinding
import com.accenture.codingassignment.model.MainActivityDataModel
import com.accenture.codingassignment.viewModel.MainActivityViewModel

/*   Main Class, responsible to show List on Screen.
 *   It fetches data from Api using MainActivityViewModel
 *   It uses MainActivityRecyclerAdapter to inflate data.
 */
class MainActivity : AppCompatActivity() {

    //activity_main.xml binding reference
    private lateinit var mActivityMainBinding: ActivityMainBinding;

    // reference variable for ViewModel Class
    private lateinit var mViewModel: MainActivityViewModel

    // reference variable of adapter which inflate list of data
    private lateinit var mAdapter: MainActivityRecyclerAdapter

    // LifeCycle Observer reference which observe APi Response
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

    // RefreshListener, that notify when a refresh is triggered via the swipe
    private fun setSwipeListener() {
        mActivityMainBinding.activityMainSwipeRefresh.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            mActivityMainBinding.activityMainSwipeRefresh.isRefreshing =
                false // if is refreshing is true, SwipeRefresh shows progress view
            getDataFromApi()
        })
    }

    private fun getDataFromApi() {
        // Network Check whether Internet is connected or not.
        if (MainApplication.isConnected) {
            callApi()
            setObserver()
        } else {
            mActivityMainBinding.activityMainSwipeRefresh.isRefreshing = false
            showErrorMsg(getString(R.string.network_error))
        }
    }


    /* show and hide progress bar
     * @show if its true, progressbar will be visible else hide
     */
    private fun showLoadingBar(show: Boolean) {
        mActivityMainBinding.activityMainPb.visibility = if (show) View.VISIBLE else View.GONE
    }


    /* show message view
     * @message This will show in Toast's textVIew.
     */
    private fun showErrorMsg(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Initialize MainActivityRecyclerAdapter and pass it's reference to recyclerView.
    private fun setAdapter() {
        mAdapter = MainActivityRecyclerAdapter()
        mActivityMainBinding.activityMainRecyclerView.adapter = mAdapter
    }

    // This method is used to initialized viewModel i.e. MainActivityViewModel, using
    // MainActivity as LifeCycleOwner
    private fun initRef() {
        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    //fetch data from MainActivityViewModel
    private fun callApi() {
        mViewModel.fetchListData()
    }

    // Set a Observer to get notify when data gets changed
    private fun setObserver() {
        showLoadingBar(true)

        // if listData has any active observer
        // then remove only those Observer which is below to this lifecycle Owner

        if (mViewModel.listData.hasActiveObservers())
            mViewModel.listData.removeObservers(this)

        mResponseObserver = Observer {

            // isDataFromApi, This value is used to notify whether data is coming
            // from api response or previously saved value
            if (mViewModel.isDataFromApi.value == true) {

                setTitle(it.title) /// Set Title for this Screen

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
