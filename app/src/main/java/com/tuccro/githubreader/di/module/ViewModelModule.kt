package com.tuccro.githubreader.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuccro.githubreader.presentation.profile.ProfileViewModel
import com.tuccro.githubreader.presentation.search.SearchViewModel
import com.tuccro.githubreader.viewmodel.base.ViewModelFactory
import com.tuccro.githubreader.viewmodel.base.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun searchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun profileViewModel(profileViewModel: ProfileViewModel): ViewModel
}