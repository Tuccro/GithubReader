package com.tuccro.githubreader.di.component

import com.tuccro.githubreader.GitHubReaderApp
import com.tuccro.githubreader.di.module.ApiModule
import com.tuccro.githubreader.di.scope.ApplicationScope
import com.tuccro.githubreader.model.GitHubDataSource
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [ApiModule::class]
)
interface ApplicationComponent {

    fun gitHubDataSource(): GitHubDataSource

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun withApp(app: GitHubReaderApp): Builder

        fun build(): ApplicationComponent
    }
}