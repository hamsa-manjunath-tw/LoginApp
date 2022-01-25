package com.tw.superapplogin

import android.content.Intent
import androidx.compose.runtime.staticCompositionLocalOf

val LocalActivityResultCallbackManager =
    staticCompositionLocalOf<ActivityResultCallbackManager> { error("No ActivityResultCallbackManager provided") }

interface ActivityResultCallbackI {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean
}

class ActivityResultCallbackManager {

    private val listeners = mutableListOf<ActivityResultCallbackI>()

    fun addListener(listener : ActivityResultCallbackI) {
        listeners.add(listener)
    }

    fun removeListener(listener : ActivityResultCallbackI) {
        listeners.remove(listener)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) : Boolean =
        listeners.any { it.onActivityResult(requestCode, resultCode, data) }
}