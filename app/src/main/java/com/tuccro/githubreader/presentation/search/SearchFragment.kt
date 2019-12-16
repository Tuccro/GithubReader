package com.tuccro.githubreader.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tuccro.githubreader.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        viewModel = activity?.run {
            ViewModelProviders.of(this)[SearchViewModel::class.java]
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