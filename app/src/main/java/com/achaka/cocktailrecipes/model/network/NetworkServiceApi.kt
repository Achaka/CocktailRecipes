package com.achaka.cocktailrecipes.model.network

import com.achaka.cocktailrecipes.model.network.dtos.*
import com.achaka.cocktailrecipes.model.network.networkresponseadapter.NetworkResponse
import com.achaka.cocktailrecipes.model.network.networkresponseadapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://the-cocktail-db.p.rapidapi.com/"
private const val HOST = "x-rapidapi-host: the-cocktail-db.p.rapidapi.com"
private const val API_KEY = "x-rapidapi-key: e3a70448e3mshd434aadb6650e4ap12b34bjsn710a0f23a9ac"

private const val searchRequest = "search.php"
private const val filterRequest = "filter.php"
private const val lookupRequest = "lookup.php"
private const val listRequest = "list.php"

val moshi: Moshi = Moshi.Builder()
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
    .addCallAdapterFactory(NetworkResponseAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .client(client)
    .build()

interface NetworkServiceApi {
    /*
    * In this part network calls made using Kotlin Coroutines
    * */
    @Headers(HOST, API_KEY)
    @GET(searchRequest)
    suspend fun getCocktailsByName(
        @Query(value = "s") name: String
    ): NetworkResponse<FullDrinkResponse, String>

    @Headers(HOST, API_KEY)
    @GET(searchRequest)
    suspend fun getIngredientByName(
        @Query(value = "i") name: String
    ): NetworkResponse<IngredientResponse, String>

    //response here - picture, name, id
    @Headers(HOST, API_KEY)
    @GET(filterRequest)
    suspend fun getCocktailsByIngredientName(
        @Query(value = "i") name: String
    ): NetworkResponse<SimpleDrinkResponse, String>

    @Headers(HOST, API_KEY)
    @GET(lookupRequest)
    suspend fun getCocktailDetailsById(
        @Query(value = "i") id: Int
    ): NetworkResponse<FullDrinkResponse, String>

    @Headers(HOST, API_KEY)
    @GET(lookupRequest)
    suspend fun getIngredientDetailsById(
        @Query(value = "iid") id: Int
    ): IngredientResponse

    //filters
    @Headers(HOST, API_KEY)
    @GET(searchRequest)
    suspend fun filterAllCocktailsByFirstLetter(
        @Query(value = "f") letter: Char
    ): FullDrinkResponse

    /*
    * In this part network calls made using Kotlin Coroutines return Simple Type
    * */
    @Headers(HOST, API_KEY)
    @GET(filterRequest)
    suspend fun filterBySeveralIngredients(
        @Query(value = "i") vararg: String
    ): SimpleDrinkResponse

    @Headers(HOST, API_KEY)
    @GET(filterRequest)
    suspend fun filterByAlcoholic(
        @Query(value = "a") type: String
    ): SimpleDrinkResponse

    @Headers(HOST, API_KEY)
    @GET(filterRequest)
    suspend fun filterByCategory(
        @Query(value = "с") category: String
    ): SimpleDrinkResponse

    @Headers(HOST, API_KEY)
    @GET(filterRequest)
    suspend fun filterByGlass(
        @Query(value = "g") glassType: String
    ): SimpleDrinkResponse

/*
* Here network data retrieved via RxJava3
* */

    @Headers(HOST, API_KEY)
    @GET(lookupRequest)
    fun getCocktailDetailsByIdRx(
        @Query(value = "i") id: Int
    ): Single<FullDrinkResponse>

    //custom requests
    @Headers(HOST, API_KEY)
    @GET("random.php")
    fun getRandomCocktail(): Single<FullDrinkResponse>

    @Headers(HOST, API_KEY)
    @GET("randomselection.php")
    fun getTenRandomCocktails(): Single<FullDrinkResponse>

    @Headers(HOST, API_KEY)
    @GET("popular.php")
    fun getPopularCocktails(): Single<FullDrinkResponse>

    @Headers(HOST, API_KEY)
    @GET("latest.php")
    fun getLatestCocktails(): Single<FullDrinkResponse>

    //lists
    @Headers(HOST, API_KEY)
    @GET("$listRequest?c=list")
    fun getListOfCategories(): Single<CategoryResponse>

    @Headers(HOST, API_KEY)
    @GET("$listRequest?g=list")
    fun getListOfGlasses(): Single<GlassesResponse>

    @Headers(HOST, API_KEY)
    @GET("$listRequest?i=list")
    fun getListOfIngredients(): Single<IngredientResponse>

    @Headers(HOST, API_KEY)
    @GET("$listRequest?a=list")
    fun getListOfAlcoholicFilters(): Single<AlcoholicListResponse>
}

object NetworkApi {
    val retrofitService: NetworkServiceApi by lazy {
        retrofit.create(NetworkServiceApi::class.java)
    }
}