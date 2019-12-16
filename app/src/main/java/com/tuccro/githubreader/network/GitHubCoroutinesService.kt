package com.tuccro.githubreader.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.tuccro.githubreader.BuildConfig
import com.tuccro.githubreader.GetRepositoriesQuery
import com.tuccro.githubreader.GetUserInfoQuery
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.coroutines.CoroutineContext

class GitHubCoroutinesService(
    private val processContext: CoroutineContext = Dispatchers.IO,
    private val resultContext: CoroutineContext = Dispatchers.Main
) : GitHubDataSource() {

    private val apolloClient: ApolloClient by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "bearer ${BuildConfig.GITHUB_OAUTH_TOKEN}")
                    .build()

                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        ApolloClient.builder()
            .serverUrl(BuildConfig.GITHUB_BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

    private var fetchUserJob: Job? = null
    private var fetchRepositoriesJob: Job? = null

    override fun fetchUser(userName: String, callback: ResultCallback<GetUserInfoQuery.User?>) {

        fetchUserJob = CoroutineScope(processContext).launch {
            try {
                val response =
                    apolloClient.query(GetUserInfoQuery(userName)).toDeferred()
                        .await()
                withContext(resultContext) {
                    callback.onResult(response.data()?.user)
                }
            } catch (e: Exception) {
                callback.onError(e)
            }
        }
    }

    fun fetchRepositories(
        userName: String,
        perPage: Int,
        cursor: String?,
        callback: ResultCallback<GetRepositoriesQuery.RepositoriesResult?>
    ) {
        fetchRepositoriesJob = CoroutineScope(processContext).launch {
            try {
                val response =
                    apolloClient.query(
                        GetRepositoriesQuery(
                            userName,
                            perPage,
                            Input.optional(cursor)
                        )
                    ).toDeferred()
                        .await()
                withContext(resultContext) {
                    callback.onResult(response.data()?.user?.repositoriesResult)
                }
            } catch (e: Exception) {
                callback.onError(e)
            }
        }
    }

    override fun cancelFetching() {
        fetchUserJob?.cancel()
        fetchRepositoriesJob?.cancel()
    }
}