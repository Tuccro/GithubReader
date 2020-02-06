package com.tuccro.githubreader.presentation.profile

import androidx.paging.PageKeyedDataSource
import com.tuccro.githubreader.GetRepositoriesQuery
import com.tuccro.githubreader.network.GitHubCoroutinesService
import com.tuccro.githubreader.network.GitHubDataSource
import timber.log.Timber

class RepositoriesDataSource(private val userName: String) :
    PageKeyedDataSource<String, GetRepositoriesQuery.Repository>() {

    private var gitHubCoroutinesService = GitHubCoroutinesService()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, GetRepositoriesQuery.Repository>
    ) {

        gitHubCoroutinesService.fetchRepositories(userName, params.requestedLoadSize, null, object :
            GitHubDataSource.ResultCallback<GetRepositoriesQuery.RepositoriesResult?> {
            override fun onResult(result: GetRepositoriesQuery.RepositoriesResult?) {
                Timber.d("onResult $result")

                callback.onResult(
                    result?.repositories ?: listOf(),
                    null,
                    result?.pageInfo?.endCursor
                )
            }

            override fun onError(e: Exception) {
                Timber.e(e)
            }
        })
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, GetRepositoriesQuery.Repository>
    ) {

        gitHubCoroutinesService.fetchRepositories(
            userName,
            params.requestedLoadSize,
            params.key,
            object :
                GitHubDataSource.ResultCallback<GetRepositoriesQuery.RepositoriesResult?> {
                override fun onResult(result: GetRepositoriesQuery.RepositoriesResult?) {
                    Timber.d("onResult $result")

                    callback.onResult(
                        result?.repositories ?: listOf(),
                        result?.pageInfo?.endCursor
                    )
                }

                override fun onError(e: Exception) {
                    Timber.e(e)
                }
            })
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, GetRepositoriesQuery.Repository>
    ) {
    }
}