package com.accenture.codingassignment.networking

import com.accenture.codingassignment.MainApplication
import com.accenture.codingassignment.R
import java.io.IOException

class NetworkException : IOException() {
    override val message: String?
        get() = MainApplication.instance.getString(R.string.network_error)
}