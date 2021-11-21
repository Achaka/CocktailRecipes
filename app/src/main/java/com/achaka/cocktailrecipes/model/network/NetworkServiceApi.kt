package com.achaka.cocktailrecipes.model.network

import retrofit2.Retrofit

private const val baseUrl = ""
private const val header = ""
private const val authToken = ""

val retrofit = Retrofit.Builder()
    .

interface NetworkServiceApi {

}

object NetworkApi {
    val retrofitService: NetworkServiceApi by lazy {
        retrofit.
    }
}