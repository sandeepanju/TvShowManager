package com.example.tvshowmanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tvshowmanager.ShowMovieListQuery
import com.example.tvshowmanager.pojo.ViewState
import com.example.tvshowmanager.repository.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
    val dataObserve: LiveData<ViewState<List<ShowMovieListQuery.Edge>>> get() = _dataObserve
    private val _dataObserve: MutableLiveData<ViewState<List<ShowMovieListQuery.Edge>>> =
        MutableLiveData()

    fun fetchShowList() {
        viewModelScope.launch {
            try {
                _dataObserve.postValue(ViewState.Loading)
                val data = repository.getShowList()
                _dataObserve.postValue(ViewState.Success(data))
            } catch (e: Exception) {
                if (e is IOException) {
                    _dataObserve.postValue(ViewState.NetworkError)
                } else {
                    _dataObserve.postValue(ViewState.Error(e.toString()))
                }
                e.printStackTrace()
            }
        }
    }
}