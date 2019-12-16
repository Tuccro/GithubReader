package com.tuccro.githubreader.network

import com.apollographql.apollo.ApolloClient
import com.tuccro.githubreader.BuildConfig
import com.tuccro.githubreader.GetUserInfoQuery
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

abstract class GitHubDataSource() {
    abstract fun fetchUser(userName: String, callback: ResultCallback<GetUserInfoQuery.User?>)
    abstract fun cancelFetching()

    interface ResultCallback<T> {
        fun onResult(result: T);
        fun onError(e: Exception);
    }
}