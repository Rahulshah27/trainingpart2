package com.example.baseprojecttest.util

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.*

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun CoroutineScope.launchPeriodically(repeatInMillis:Long, action: ()->Unit)
        = this.launch {
    withContext(Dispatchers.Main) {
        if (repeatInMillis > 0) {
            while (isActive) {
                action()
                delay(repeatInMillis)
            }
        } else {
            action()
        }
    }
}