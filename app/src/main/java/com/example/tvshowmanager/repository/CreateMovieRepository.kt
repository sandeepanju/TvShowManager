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

class CreateMovieRepository(val client: ApolloClient) {

    private lateinit var createMovieResult: CreateMovieMutation.Movie

    suspend fun createNewShow(
        title: String,
        releaseDate: Date,
        seasons: String
    ): CreateMovieMutation.Movie {
        withContext(Dispatchers.IO) {
            val response =
                client.mutate( CreateMovieMutation(
                    CreateMovieInput(
                        Input.optional(
                            CreateMovieFieldsInput(
                                title = title,
                                releaseDate = Input.optional(releaseDate),
                                seasons = Input.optional(seasons.toDouble())
                            )
                        )
                    )
                )).await()
            createMovieResult = response.data!!.createMovie!!.movie
        }
        return createMovieResult
    }
}