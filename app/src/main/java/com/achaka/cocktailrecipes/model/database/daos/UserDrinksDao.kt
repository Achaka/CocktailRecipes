package com.achaka.cocktailrecipes.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import com.achaka.cocktailrecipes.model.database.entities.DatabaseUserDrink

@Dao
interface UserDrinksDao {

    @Insert
    fun insertUserDrink(drink: DatabaseUserDrink)
}