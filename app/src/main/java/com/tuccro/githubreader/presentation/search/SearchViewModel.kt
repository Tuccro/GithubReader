package com.tuccro.githubreader.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SearchViewModel @Inject constructor() : ViewModel() {

    val query: MutableLiveData<String> = MutableLiveData()
}