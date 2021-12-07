package com.achaka.cocktailrecipes.model.repository

import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.*
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.FullDrinkResponse
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.*

class DrinkRepository(private val database: CocktailsAppDatabase) {

    fun insertDrink(drink: DatabaseDrink) {
        database.drinksDao().insertDrink(drink)
    }

    fun getTenRandomCocktails(): Single<FullDrinkResponse> {
        return NetworkApi.retrofitService.getTenRandomCocktails()
    }

    fun getPopularCocktails(): Single<FullDrinkResponse> {
        return NetworkApi.retrofitService.getPopularCocktails()
    }

    fun getDrinkById(drinkId: Int): Flow<DatabaseDrink?> {
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
            fetchAndInsert(drinkItem.id)
        }
        if (drinkItem is UserDrink) {
            val favourite = Favourite(drinkId = drinkItem.id, isUserDrink = true)
            database.drinksDao().addToFavourites(favourite)
        }
    }

    private suspend fun fetchAndInsert(drinkId: Int) {
        val drinkResponse = NetworkApi.retrofitService.getCocktailDetailsById(drinkId)
        database.drinksDao().insertDrink(drinkResponse.asDatabaseModel()[0])
    }

    fun getAllFavourites(): Flow<List<Favourite>> {
        return database.drinksDao().getAllFavourites()
    }

    suspend fun removeFromFavourites(drinkId: Int) {
        database.drinksDao().removeFromFavourites(drinkId)
    }

    //Recent section
    fun insertRecentItem(recent: Recent): Single<Long> {
        return database.drinksDao().insertRecentItem(recent)
    }


    fun getRecentDrinks(): Observable<List<Recent>> {
        return database.drinksDao().getRecentItems()
    }


    fun removeRecentItem(timestamp: Long): Completable {
        return database.drinksDao().removeRecentItem(timestamp)
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