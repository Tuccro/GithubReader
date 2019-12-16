package com.tuccro.githubreader

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import timber.log.Timber
import timber.log.Timber.DebugTree

class GitHubReaderApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initFresco()
        initTimber()
    }

    private fun initFresco() {
        Fresco.initialize(this)

    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}