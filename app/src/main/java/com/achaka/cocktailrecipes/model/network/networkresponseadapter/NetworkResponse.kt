package com.achaka.cocktailrecipes.model.network.networkresponseadapter

import java.io.IOException

sealed class NetworkResponse<out T : Any, out U : Any> {
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()
    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()
    data class NetworkError(val error: String) : NetworkResponse<Nothing, Nothing>()
    data class UnknownError(val error: String) : NetworkResponse<Nothing, Nothing>()
}
