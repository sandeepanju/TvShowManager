package com.example.tvshowmanager.di

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.example.tvshowmanager.repository.CreateMovieRepository
import com.example.tvshowmanager.repository.MovieRepository
import com.example.tvshowmanager.viewModel.CreateMovieViewModel
import com.example.tvshowmanager.viewModel.MovieViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val viewModelModule = module {
    factory { MovieViewModel(get()) }
    factory { CreateMovieViewModel(get()) }
}

val netModule = module {
    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHttpClient(cache: Cache): OkHttpClient {
        val httpBuilder =
            OkHttpClient.Builder().cache(cache).addNetworkInterceptor(NetworkInterceptor())
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpBuilder.interceptors().add(httpLoggingInterceptor)
        return httpBuilder.build()
    }

    fun provideApolloClient(client: OkHttpClient) =
        ApolloClient.builder().serverUrl("https://tv-show-manager.combyne.com/graphql")
            .okHttpClient(client).build()

    single { provideCache(androidApplication()) }
    single { provideHttpClient(get()) }
    single { provideApolloClient(get()) }
}

val repositoryModule = module {
    single { MovieRepository(get()) }
    single { CreateMovieRepository(get()) }
}

