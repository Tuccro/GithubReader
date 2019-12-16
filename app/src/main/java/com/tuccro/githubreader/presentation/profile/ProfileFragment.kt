package com.tuccro.githubreader.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tuccro.githubreader.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var viewModel: ProfileViewModel
    private val adapter = RepositoriesListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        viewModel = activity?.run {
            ViewModelProviders.of(this)[ProfileViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentProfileBinding.inflate(inflater, container, false)
        .apply {
            viewModel = this@ProfileFragment.viewModel
            rvRepos.layoutManager = LinearLayoutManager(context)
            rvRepos.adapter = adapter
        }.root

    private fun observeLiveData() {
        //observe live data emitted by view model
        viewModel.getRepositories()?.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.start(requireArguments())
            observeLiveData()
        }
    }
}