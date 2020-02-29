package com.tuccro.githubreader

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.tuccro.githubreader.di.component.ApplicationComponent
import com.tuccro.githubreader.di.component.DaggerApplicationComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class GitHubReaderApp : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
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

    private fun initDagger() {
        appComponent = DaggerApplicationComponent
            .builder()
            .withApp(this)
            .build()
    }
}