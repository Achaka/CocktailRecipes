package com.achaka.cocktailrecipes.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.achaka.cocktailrecipes.data.database.entities.DatabaseUserDrink
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDrinksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserDrink(drink: DatabaseUserDrink)

    @Query("SELECT * FROM user_drinks_table WHERE id =:drinkId")
    fun getUserDrinkById(drinkId: Int): Flow<DatabaseUserDrink>

    @Query("SELECT * FROM user_drinks_table WHERE id IN (:drinkId)")
    fun getUserDrinksById(drinkId: List<Int>): Flow<List<DatabaseUserDrink>>
}