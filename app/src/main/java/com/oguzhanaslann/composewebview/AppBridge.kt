package com.oguzhanaslann.composewebview

import android.content.Context
import android.os.Build
import android.webkit.JavascriptInterface
import android.widget.Toast

/** Bridge between web content and Android app functionality */
class AppBridge(private val context: Context) {

    /** Display a notification message from web content */
    @JavascriptInterface
    fun displayMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    
    /** Get device information accessible to web */
    @JavascriptInterface
    fun getDeviceInfo(): String {
        return "Android ${Build.VERSION.RELEASE}"
    }
}