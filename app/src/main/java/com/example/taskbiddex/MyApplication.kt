package com.example.taskbiddex

import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.app.Application
import android.content.Context
import android.os.Bundle
import dagger.hilt.android.HiltAndroidApp

/*
    created at 01/01/2024
    by Abdallah Marwad
    abdallahshehata311as@gmail.com
 */
@HiltAndroidApp
class MyApplication : Application() {
    private var currentAct: Activity? = null
    companion object{
        lateinit var myAppContext: MyApplication
            private set
    }

    override fun onCreate() {
        super.onCreate();
        myAppContext = this.applicationContext as MyApplication
        actInstance()

    }

    fun getCurrentAct(): Activity? {
        return currentAct
    }

     fun isAppInBackground(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return true
        for (appProcess in appProcesses) {
            if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName == packageName) {
                return false
            }
        }
        return true
    }
    private fun actInstance() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
            }

            override fun onActivityStarted(p0: Activity) {
                currentAct = p0
            }

            override fun onActivityResumed(p0: Activity) {
                currentAct = p0
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
//                if (currentAct == activity) {
//                    currentAct = null
//                }
            }

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityDestroyed(p0: Activity) {
            }

        })
    }


}