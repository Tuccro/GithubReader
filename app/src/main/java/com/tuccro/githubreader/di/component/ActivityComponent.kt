package com.tuccro.githubreader.di.component

import android.app.Activity
import com.tuccro.githubreader.di.module.ViewModelModule
import com.tuccro.githubreader.di.scope.ActivityScope
import com.tuccro.githubreader.presentation.base.BaseFragment
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(
    modules = [ViewModelModule::class],
    dependencies = [ApplicationComponent::class]
)
interface ActivityComponent {
    fun inject(fragment: BaseFragment)

    @Component.Builder
    interface Builder {


        @BindsInstance
        fun withActivity(activity: Activity): Builder

        fun withAppComponent(appComponent: ApplicationComponent): Builder

        fun build(): ActivityComponent
    }
}