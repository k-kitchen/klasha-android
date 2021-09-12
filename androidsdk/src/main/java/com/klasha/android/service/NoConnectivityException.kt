package com.klasha.android.service

import com.klasha.android.model.Error
import java.io.IOException

class NoConnectivityException: IOException() {
    override val message: String
        get() = Error.NO_INTERNET.toString()
}