package com.tuccro.githubreader.model

import com.apollographql.apollo.ApolloClient
import com.tuccro.githubreader.GetRepositoriesQuery
import com.tuccro.githubreader.GetUserInfoQuery

abstract class GitHubDataSource constructor(protected open val apolloClient: ApolloClient) {

    abstract fun fetchUser(userName: String, callback: ResultCallback<GetUserInfoQuery.User?>)

    abstract fun fetchRepositories(
        userName: String,
        perPage: Int,
        cursor: String?,
        callback: ResultCallback<GetRepositoriesQuery.RepositoriesResult?>
    )

    abstract fun cancelFetching()

    interface ResultCallback<T> {
        fun onResult(result: T)
        fun onError(e: Exception)
    }
}