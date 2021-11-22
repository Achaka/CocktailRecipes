package com.achaka.cocktailrecipes.model.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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

    @Headers(HOST, API_KEY)
    @GET(searchRequest)
    suspend fun getCocktailsByName(
        @Query(value = "s") name: String
    )
    //list<full drink response>

    @Headers(HOST, API_KEY)
    @GET(searchRequest)
    suspend fun getIngredientByName(
        @Query(value = "i") name: String
    )
    //list<full ingredient response>

    //response here - picture, name, id
    @Headers(HOST, API_KEY)
    @GET(filterRequest)
    suspend fun getCocktailsByIngredientName(
        @Query(value = "i") name: String
    )
    //List<simple drink response>

    @Headers(HOST, API_KEY)
    @GET(lookupRequest)
    suspend fun getCocktailDetailsById(
        @Query(value = "i") id: Int
    )
    //List<full drink response>

    @Headers(HOST, API_KEY)
    @GET(lookupRequest)
    suspend fun getIngredientDetailsById(
        @Query(value = "iid") id: Int
    )
    //List<full Ingredients response>

    //random
    @Headers(HOST, API_KEY)
    @GET("random.php")
    suspend fun getRandomCocktail()
    //List<full drink response>

    @Headers(HOST, API_KEY)
    @GET("randomselection.php")
    suspend fun getTenRandomCocktails()
    //List<full drink response>

    //popular
    @Headers(HOST, API_KEY)
    @GET("popular.php")
    suspend fun getPopularCocktails()
    //List<full drink response>

    //latest
    @Headers(HOST, API_KEY)
    @GET("latest.php")
    suspend fun getLatestCocktails()
    //List<full drink response>

    //filters
    @Headers(HOST, API_KEY)
    @GET(searchRequest)
    suspend fun filterAllCocktailsByFirstLetter(
        @Query(value = "f") letter: Char
    )
    //List<full drink response>

    @Headers(HOST, API_KEY)
    @GET(filterRequest)
    suspend fun filterBySeveralIngredients(
        @Query(value = "i") vararg: String
    )

    @Headers(HOST, API_KEY)
    @GET(filterRequest)
    suspend fun filterByAlcoholic(
        @Query(value = "a") type: String
    )
    //List<simple drink response>

    @Headers(HOST, API_KEY)
    @GET(filterRequest)
    suspend fun filterByCategory(
        @Query(value = "a") category: String
    )
    //List<simple drink response>

    @Headers(HOST, API_KEY)
    @GET(filterRequest)
    suspend fun filterByGlass(
        @Query(value = "g") glassType: String
    )
    //List<simple drink response>

    //lists
    @Headers(HOST, API_KEY)
    @GET("$listRequest?c=list")
    suspend fun getListOfCategories()
    //list<categories response>

    @Headers(HOST, API_KEY)
    @GET("$listRequest?g=list")
    suspend fun getListOfGlasses()
    //list<glasses response>

    @Headers(HOST, API_KEY)
    @GET("$listRequest?i=list")
    suspend fun getListOfIngredients()
    //list<ingredients response>

    @Headers(HOST, API_KEY)
    @GET("$listRequest?a=list")
    suspend fun getListOfAlcoholicFilters()
    //list<alcoholic response>
}

object NetworkApi {
    val retrofitService: NetworkServiceApi by lazy {
        retrofit.create(NetworkServiceApi::class.java)
    }
}