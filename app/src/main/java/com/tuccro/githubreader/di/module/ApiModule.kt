package com.tuccro.githubreader.di.module

import com.apollographql.apollo.ApolloClient
import com.tuccro.githubreader.BuildConfig
import com.tuccro.githubreader.di.scope.ApplicationScope
import com.tuccro.githubreader.network.GitHubCoroutinesService
import com.tuccro.githubreader.model.GitHubDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class ApiModule {

    @Provides
    @ApplicationScope
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(BuildConfig.GITHUB_BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
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

    }

    @Provides
    @ApplicationScope
    fun provideGitHubDataSource(apolloClient: ApolloClient): GitHubDataSource {
        return GitHubCoroutinesService(apolloClient)
    }
}