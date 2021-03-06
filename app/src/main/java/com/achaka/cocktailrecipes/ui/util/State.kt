package com.achaka.cocktailrecipes.ui.util

sealed class State<out T> {
    data class Success<T : Any>(val data: T) : State<T>()
    data class Error(val exceptionMessage: String) : State<Nothing>()
    object Loading : State<Nothing>()
}
