package com.tuccro.githubreader.presentation.profile

import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.tuccro.githubreader.GetRepositoriesQuery
import com.tuccro.githubreader.GetUserInfoQuery
import com.tuccro.githubreader.network.GitHubCoroutinesService
import com.tuccro.githubreader.network.GitHubDataSource
import timber.log.Timber

class ProfileViewModel : ViewModel() {

    val PAGE_SIZE = 10
    val avatarUrl = ObservableField<String>()
    val name = ObservableField<String>()
    val followers = ObservableField<Int>(0)
    val following = ObservableField<Int>(0)

    var repositoriesLiveData: LiveData<PagedList<GetRepositoriesQuery.Repository>>? = null

    val config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setEnablePlaceholders(false)
        .build()

    fun getRepositories(): LiveData<PagedList<GetRepositoriesQuery.Repository>>? =
        repositoriesLiveData

    private fun initializedPagedListBuilder(userName: String):
            LivePagedListBuilder<String, GetRepositoriesQuery.Repository> {

        val dataSourceFactory =
            object : DataSource.Factory<String, GetRepositoriesQuery.Repository>() {
                override fun create(): DataSource<String, GetRepositoriesQuery.Repository> {
                    return RepositoriesDataSource(userName)
                }
            }
        return LivePagedListBuilder<String, GetRepositoriesQuery.Repository>(
            dataSourceFactory,
            config
        )
    }

    private var gitHubCoroutinesService = GitHubCoroutinesService()

    fun start(args: Bundle) {
        val userName = ProfileFragmentArgs.fromBundle(args).userName
        if (!TextUtils.isEmpty(userName)) {
            fetchUser(userName!!)
            fetchRepositories(userName)
        }
    }

    private fun fillUser(user: GetUserInfoQuery.User?) {

        if (user != null) {

            val nameCompany: StringBuilder = StringBuilder().apply {
                append(user.login)
                if (user.company != null) {
                    append(", ${user.company}")
                }
            }

            name.set(nameCompany.toString())
            avatarUrl.set(user.avatarUrl as String)
            followers.set(user.followers.totalCount)
            following.set(user.following.totalCount)
        } else {
            name.set("")
            avatarUrl.set(null)
            followers.set(0)
            following.set(0)
        }
    }

    private fun fetchUser(userName: String) {
        Timber.d("userName $userName")

        gitHubCoroutinesService.fetchUser(userName, object :
            GitHubDataSource.ResultCallback<GetUserInfoQuery.User?> {
            override fun onResult(result: GetUserInfoQuery.User?) {
                Timber.d("onResult $result")
                fillUser(result)
            }

            override fun onError(e: Exception) {
                Timber.e(e)
            }
        })
    }

    private fun fetchRepositories(userName: String) {
        repositoriesLiveData = initializedPagedListBuilder(userName).build()
    }

    override fun onCleared() {
        super.onCleared()
        gitHubCoroutinesService.cancelFetching()
    }
}