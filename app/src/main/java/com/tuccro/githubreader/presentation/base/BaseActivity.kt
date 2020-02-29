package com.tuccro.githubreader.presentation.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tuccro.githubreader.GitHubReaderApp
import com.tuccro.githubreader.di.component.ActivityComponent
import com.tuccro.githubreader.di.component.DaggerActivityComponent

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    lateinit var activityComponent: ActivityComponent
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent = DaggerActivityComponent
            .builder()
            .withAppComponent((application as GitHubReaderApp).appComponent)
            .withActivity(this)
            .build()
    }
}