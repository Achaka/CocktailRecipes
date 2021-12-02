package com.achaka.cocktailrecipes.model.repository

import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.Favourite
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.UserDrink
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel

class DrinkDetailsRepository(val database: CocktailsAppDatabase) {

    suspend fun addToFavourites(drinkItem: DrinkItem) {
        //add to favourites and to database
        if (drinkItem is Drink) {
            val favourite = Favourite(0, drinkId = drinkItem.id, isUserDrink = false)
            database.drinksDao().addToFavourites(favourite)
            fetchAndInsert(drinkItem.id)
        }

        if (drinkItem is UserDrink) {
            val favourite = Favourite(0, drinkId = drinkItem.id, isUserDrink = true)
            database.drinksDao().addToFavourites(favourite)
        }

        //insert into db
        //if it is a user drink - it is already in db, then do nothing
        //            database.drinksDao().insertDrink()

    }
    suspend fun fetchAndInsert(drinkId: Int) {
        val drinkResponse = NetworkApi.retrofitService.getCocktailDetailsById(drinkId)
        database.drinksDao().insertDrink(drinkResponse.asDatabaseModel()[0])
    }
}