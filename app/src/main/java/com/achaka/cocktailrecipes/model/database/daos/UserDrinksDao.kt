package com.achaka.cocktailrecipes.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.achaka.cocktailrecipes.model.database.entities.DatabaseUserDrink
import com.achaka.cocktailrecipes.model.domain.UserDrink
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDrinksDao {

    @Insert
    fun insertUserDrink(drink: DatabaseUserDrink)

    @Query("SELECT * FROM user_drinks_table WHERE id =:drinkId")
    fun getUserDrinkById(drinkId: Int): Flow<DatabaseUserDrink>

    @Query("SELECT * FROM user_drinks_table WHERE id IN (:drinkId)")
    fun getUserDrinksById(drinkId: List<Int>): Flow<List<DatabaseUserDrink>>
}