package com.tuccro.githubreader.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.tuccro.githubreader.databinding.FragmentSearchBinding
import com.tuccro.githubreader.presentation.base.BaseFragment

class SearchFragment : BaseFragment() {

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this, factory)[SearchViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentSearchBinding.inflate(inflater, container, false).apply {
        this.viewModel = this@SearchFragment.viewModel
        this.fragment = this@SearchFragment
    }.root

    fun openProfile(userName: String?) {
        val action = SearchFragmentDirections.actionSearchFragmentToProfileFragment(userName)
        findNavController().navigate(action)
    }
}