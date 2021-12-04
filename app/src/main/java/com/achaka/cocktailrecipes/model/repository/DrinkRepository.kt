package com.achaka.cocktailrecipes.model.repository

import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.DatabaseDrink
import com.achaka.cocktailrecipes.model.database.entities.Favourite
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.*

class DrinkRepository(private val database: CocktailsAppDatabase) {

    fun insertDrink(drink: DatabaseDrink) {
        database.drinksDao().insertDrink(drink)
    }

    fun getTenRandomCocktails(): Single<List<Drink>> {
        return NetworkApi.retrofitService.getTenRandomCocktails().map {
            it.asDatabaseModel().asDomainModel()
        }
    }

    fun getPopularCocktails(): Single<List<Drink>> {
        return NetworkApi.retrofitService.getPopularCocktails()
            .map { it.asDatabaseModel().asDomainModel() }
    }

//    fun getDrinkById(drinkId: Int): Flow<DatabaseDrink> {
//        return database.drinksDao().getDrinkById(drinkId)
//    }

    fun getDrinksById(drinkId: List<Int>): Flow<List<DatabaseDrink>?> {
        return database.drinksDao().getDrinksById(drinkId)
    }

    //Favourites Section

    suspend fun addToFavourites(drinkItem: DrinkItem) {
        //add to favourites and to the database
        if (drinkItem is Drink) {
            val favourite = Favourite(0, drinkId = drinkItem.id, isUserDrink = false)
            database.drinksDao().addToFavourites(favourite)
            fetchAndInsert(drinkItem.id)
        }
        if (drinkItem is UserDrink) {
            val favourite = Favourite(0, drinkId = drinkItem.id, isUserDrink = true)
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
}