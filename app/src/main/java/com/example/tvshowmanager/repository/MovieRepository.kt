package com.example.tvshowmanager.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.example.tvshowmanager.CreateMovieMutation
import com.example.tvshowmanager.ShowMovieListQuery
import com.example.tvshowmanager.type.CreateMovieFieldsInput
import com.example.tvshowmanager.type.CreateMovieInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class MovieRepository(val client: ApolloClient) {

    suspend fun getShowList(): List<ShowMovieListQuery.Edge> {
        var result: List<ShowMovieListQuery.Edge>
        withContext(Dispatchers.IO) {
            val response = client.query(ShowMovieListQuery()).await()
            result = response.data!!.movies.edges as List<ShowMovieListQuery.Edge>
        }
        return result
    }
}