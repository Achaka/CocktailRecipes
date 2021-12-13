package com.achaka.cocktailrecipes.model.repository

import android.util.Log
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.*
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.FullDrinkResponse
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.network.networkresponseadapter.NetworkResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.*

class DrinkRepository(private val database: CocktailsAppDatabase) {

    private fun insertDrink(drink: DatabaseDrink) {
        database.drinksDao().insertDrink(drink)
    }

    fun getTenRandomCocktails(): Single<List<Drink>> {
        return NetworkApi.retrofitService.getTenRandomCocktails()
            .map { it.asDatabaseModel().asDomainModel() }
    }

    fun getPopularCocktails(): Single<List<Drink>> {
        return NetworkApi.retrofitService.getPopularCocktails()
            .map { it.asDatabaseModel().asDomainModel() }
    }

    private fun getDrinkById(drinkId: Int): DatabaseDrink? {
        return database.drinksDao().getDrinkById(drinkId)
    }

    fun getDrinksById(drinkId: List<Int>): Flow<List<DatabaseDrink>?> {
        return database.drinksDao().getDrinksById(drinkId)
    }

    fun getDrinkByIdRx(drinkId: Int): Single<Drink> {
        return database.drinksDao().getDrinkByIdRx(drinkId).map { it.asDomainModel() }
    }

    fun deleteDrinkById(drinkId: Int) {
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


    suspend fun getFavourites(): Flow<State<Drink>> = flow {

        emit(State.Loading)

        val ids = getFavouriteIds().map { it.drinkId }

        if (ids.isNullOrEmpty()) {
            emit(State.Error("No favourites yet!"))
        } else {
            ids.forEach { drinkId ->
                val drinkById = getDrinkById(drinkId)
                if (drinkById != null) {
                    //ok
                    emit(State.Success(drinkById.asDomainModel()))
                } else {
                    val networkResult = fetch(drinkId)
                    when (networkResult) {
                        is NetworkResponse.Success -> {
                            insertDrink(networkResult.body.response[0].asDatabaseModel())
                            val databaseDrink = getDrinkById(drinkId)
                            if (databaseDrink != null) {
                                emit(State.Success(databaseDrink.asDomainModel()))
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
            }
        }
    }


    private suspend fun fetch(drinkId: Int): NetworkResponse<FullDrinkResponse, String> {
        val drinkResponse = NetworkApi.retrofitService.getCocktailDetailsById(drinkId)
        when (drinkResponse) {
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

////Recent section
//fun insertRecentItem(recent: Recent): Single<Long> {
//    return database.drinksDao().insertRecentItem(recent)
//}
//
//
//fun getRecentDrinks(): Observable<List<Recent>> {
//    return database.drinksDao().getRecentItems()
//}
//
//
//fun removeRecentItem(timestamp: Long): Completable {
//    return database.drinksDao().removeRecentItem(timestamp)
//}

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