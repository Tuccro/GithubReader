package com.tuccro.githubreader.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toDeferred
import com.tuccro.githubreader.GetRepositoriesQuery
import com.tuccro.githubreader.GetUserInfoQuery
import com.tuccro.githubreader.model.GitHubDataSource
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GitHubCoroutinesService @Inject constructor(override val apolloClient: ApolloClient) :
    GitHubDataSource(apolloClient) {

    private val processContext: CoroutineContext = Dispatchers.IO
    private val resultContext: CoroutineContext = Dispatchers.Main

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

    override fun fetchRepositories(
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