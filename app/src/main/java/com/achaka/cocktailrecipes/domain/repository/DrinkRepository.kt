package com.achaka.cocktailrecipes.domain.repository

import com.achaka.cocktailrecipes.data.database.entities.DatabaseDrink

interface DrinkRepository {

    fun insertDrink(drink: DatabaseDrink)

    fun getDrinkById(drinkId: Int): DatabaseDrink?

    fun getDrinksById(drinkId: List<Int>): List<DatabaseDrink>?

    fun deleteDrinkById(drinkId: Int)
}