package com.dicoding.tourismapp

import android.app.Application
import com.dicoding.tourismapp.utils.ReleaseTreeTimber
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
open class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }else {
            Timber.plant(ReleaseTreeTimber())
        }
    }
}