package com.example.tvshowmanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvshowmanager.CreateMovieMutation
import com.example.tvshowmanager.pojo.ViewState
import com.example.tvshowmanager.repository.CreateMovieRepository
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

class CreateMovieViewModel(private val repository: CreateMovieRepository) : ViewModel() {
    val movieDataObserver: LiveData<ViewState<CreateMovieMutation.Movie>> get() = _movieDataObserver
    private val _movieDataObserver: MutableLiveData<ViewState<CreateMovieMutation.Movie>> =
        MutableLiveData()

    fun createNewShow(title: String, releaseDate: Date, seasons: String) {
        viewModelScope.launch {
            try {
                _movieDataObserver.postValue(ViewState.Loading)
                val data = repository.createNewShow(title, releaseDate, seasons)
                _movieDataObserver.postValue(ViewState.Success(data))
            } catch (e: Exception) {
                if (e is IOException) {
                    _movieDataObserver.postValue(ViewState.NetworkError)
                } else {
                    _movieDataObserver.postValue(ViewState.Error(e.toString()))
                }
                e.printStackTrace()
            }
        }
    }
}