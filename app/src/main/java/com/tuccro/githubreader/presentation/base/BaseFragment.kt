package com.tuccro.githubreader.presentation.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tuccro.githubreader.viewmodel.base.ViewModelFactory
import javax.inject.Inject

open class BaseFragment : Fragment() {

    protected lateinit var factory: ViewModelFactory
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectSelf()
        retainInstance = true
    }

    private fun injectSelf() {
        (activity as BaseActivity).activityComponent.inject(this)
    }
}