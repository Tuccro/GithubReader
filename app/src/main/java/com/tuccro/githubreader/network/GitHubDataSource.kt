package com.tuccro.githubreader.network

import com.tuccro.githubreader.GetUserInfoQuery

abstract class GitHubDataSource {
    abstract fun fetchUser(userName: String, callback: ResultCallback<GetUserInfoQuery.User?>)
    abstract fun cancelFetching()

    interface ResultCallback<T> {
        fun onResult(result: T)
        fun onError(e: Exception)
    }
}