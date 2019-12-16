package com.tuccro.githubreader.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    val query: MutableLiveData<String> = MutableLiveData()
}