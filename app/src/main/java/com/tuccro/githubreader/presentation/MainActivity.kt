package com.tuccro.githubreader.presentation

import android.os.Bundle
import com.tuccro.githubreader.R
import com.tuccro.githubreader.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
