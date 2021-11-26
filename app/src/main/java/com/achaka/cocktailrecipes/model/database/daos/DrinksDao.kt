package com.achaka.cocktailrecipes.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import com.achaka.cocktailrecipes.model.domain.Drink

@Dao
interface DrinksDao {
    @Insert
    fun insertDrink(drink: Drink) {

    }

    fun getDrink() {

    }

    fun getDrinksList() {

    }

    fun updateDrink() {

    }
}