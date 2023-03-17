package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.app.Application
import android.app.Activity

class MyApplication : Application() {

    private var currentActivity: Activity? = null

    fun setCurrentActivity(activity: Activity?) {
        currentActivity = activity
    }

    fun getCurrentActivity(): Activity? {
        return currentActivity
    }

}
