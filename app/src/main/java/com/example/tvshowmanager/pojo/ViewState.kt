package com.example.tvshowmanager.pojo

sealed class ViewState<out T>{
    object Loading : ViewState<Nothing>()
    object NetworkError : ViewState<Nothing>()
    data class Success<T>(val data : T) : ViewState<T>()
    data class Error(val data : String) : ViewState<Nothing>()
}
