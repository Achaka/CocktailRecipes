package com.achaka.cocktailrecipes.model.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://the-cocktail-db.p.rapidapi.com/"
private const val HOST = "x-rapidapi-host: the-cocktail-db.p.rapidapi.com"
private const val API_KEY = "x-rapidapi-key: e3a70448e3mshd434aadb6650e4ap12b34bjsn710a0f23a9ac"

val moshi = Moshi.Builder()
    .add(RxJava3CallAdapterFactory.create())
    .add(KotlinJsonAdapterFactory())
    .build()

val loggingInterceptor = HttpLoggingInterceptor()

val client = OkHttpClient().newBuilder()
    .addInterceptor(
        loggingInterceptor.setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
    )
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .client(client)
    .build()

interface NetworkServiceApi {

    //

}

object NetworkApi {
    val retrofitService: NetworkServiceApi by lazy {
        retrofit.create(NetworkServiceApi::class.java)
    }
}