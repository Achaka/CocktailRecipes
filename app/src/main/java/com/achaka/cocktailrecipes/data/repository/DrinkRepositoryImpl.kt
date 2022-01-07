package com.achaka.cocktailrecipes.data.repository

import android.util.Log
import com.achaka.cocktailrecipes.ui.util.State
import com.achaka.cocktailrecipes.data.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.data.database.entities.*
import com.achaka.cocktailrecipes.domain.model.Drink
import com.achaka.cocktailrecipes.domain.model.DrinkItem
import com.achaka.cocktailrecipes.domain.model.UserDrink
import com.achaka.cocktailrecipes.data.network.NetworkServiceApi
import com.achaka.cocktailrecipes.data.network.dtos.FullDrinkResponse
import com.achaka.cocktailrecipes.data.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.data.network.networkresponseadapter.NetworkResponse
import com.achaka.cocktailrecipes.domain.repository.DrinkRepository
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class DrinkRepositoryImpl @Inject constructor(
    private val database: CocktailsAppDatabase,
    private val networkApi: NetworkServiceApi
) : DrinkRepository {

    fun getTenRandomCocktails(): Single<List<Drink>> {
        return networkApi.getTenRandomCocktails()
            .map { it.asDatabaseModel().asDomainModel() }
    }

    fun getPopularCocktails(): Single<List<Drink>> {
        return networkApi.getPopularCocktails()
            .map { it.asDatabaseModel().asDomainModel() }
    }

    override fun insertDrink(drink: DatabaseDrink) {
        database.drinksDao().insertDrink(drink)
    }

    override fun getDrinkById(drinkId: Int): DatabaseDrink? {
        return database.drinksDao().getDrinkById(drinkId)
    }

    override fun getDrinksById(drinkId: List<Int>): List<DatabaseDrink>? {
        return database.drinksDao().getDrinksById(drinkId)
    }

    override fun deleteDrinkById(drinkId: Int) {
        database.drinksDao().deleteDrinkById(drinkId)
    }

    //Favourites Section

    suspend fun addToFavourites(drinkItem: DrinkItem) {
        //add to favourites and to the database
        if (drinkItem is Drink) {
            val favourite = Favourite(drinkId = drinkItem.id, isUserDrink = false)
            database.drinksDao().addToFavourites(favourite)
            val fetchDrinkResult = fetch(drinkItem.id)
            if (fetchDrinkResult is NetworkResponse.Success) {
                insertDrink(fetchDrinkResult.body.response[0].asDatabaseModel())
            }
        }
        if (drinkItem is UserDrink) {
            val favourite = Favourite(drinkId = drinkItem.id, isUserDrink = true)
            database.drinksDao().addToFavourites(favourite)
        }
    }


    suspend fun getFavourites(): Flow<State<List<Drink>>> = flow {

        emit(State.Loading)

        val ids = getFavouriteIds().map { it.drinkId }

        if (ids.isNullOrEmpty()) {
            emit(State.Error("No favourites yet!"))
        } else {
            ids.map { drinkId ->
                val drinkById = getDrinkById(drinkId)
                if (drinkById == null)
                    when (val networkResult = fetch(drinkId)) {
                        is NetworkResponse.Success -> {
                            insertDrink(networkResult.body.response[0].asDatabaseModel())
                            val databaseDrink = getDrinkById(drinkId)
                            if (databaseDrink != null) {
                                //
                            } else {
                                emit(State.Error("Could not load $drinkId"))
                            }
                        }
                        is NetworkResponse.ApiError -> {
                            emit(State.Error("Error ${networkResult.code}"))
                        }
                        is NetworkResponse.NetworkError -> {
                            emit(State.Error("Could not load $drinkId: Network Error"))
                        }
                        is NetworkResponse.UnknownError -> {
                            emit(State.Error("Could not load $drinkId: Unknown Error"))
                        }
                    }
            }
            val favouriteIds = getFavouriteIds().map { it.drinkId }
            val favouriteDrinks = getDrinksById(favouriteIds)
            if (favouriteDrinks != null) {
                emit(State.Success(favouriteDrinks.map { it.asDomainModel() }))
            }
        }
    }


    private suspend fun fetch(drinkId: Int): NetworkResponse<FullDrinkResponse, String> {
        when (val drinkResponse = networkApi.getCocktailDetailsById(drinkId)) {
            is NetworkResponse.Success -> {
                return drinkResponse
            }
            is NetworkResponse.ApiError<String> -> {
                Log.d("Network response api error", drinkResponse.code.toString())
                return NetworkResponse.ApiError(drinkResponse.body, drinkResponse.code)
            }
            is NetworkResponse.NetworkError -> {
                Log.d("Network error", "Network Error")
                return NetworkResponse.NetworkError("Network Error")
            }
            is NetworkResponse.UnknownError -> {
                Log.d("Unknown Error", "Unknown Error")
                return NetworkResponse.UnknownError("Unknown Error")
            }
        }
    }

    private fun getFavouriteIds(): List<Favourite> {
        return database.drinksDao().getFavouriteIds()
    }

    fun getFavouriteIdsFlow(): Flow<List<Favourite>> {
        return database.drinksDao().getFavouriteIdsFlow()
    }

    fun removeFromFavourites(drinkId: Int) {
        database.drinksDao().removeFromFavourites(drinkId)
    }

    //commentaries section
    fun addCommentary(commentary: Commentary) {
        database.drinksDao().addCommentary(commentary)
    }

    fun getCommentaries(): Flow<List<Commentary>> {
        return database.drinksDao().getCommentaries()
    }

    fun getCommentaryById(drinkId: Int): Flow<Commentary> {
        return database.drinksDao().getCommentary(drinkId)
    }

    fun removeCommentary(drinkId: Int) {
        database.drinksDao().removeCommentary(drinkId)
    }
}