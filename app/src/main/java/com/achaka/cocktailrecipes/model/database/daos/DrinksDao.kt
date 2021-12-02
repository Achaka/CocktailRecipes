package com.achaka.cocktailrecipes.model.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.achaka.cocktailrecipes.model.database.entities.Commentary
import com.achaka.cocktailrecipes.model.database.entities.DatabaseDrink
import com.achaka.cocktailrecipes.model.database.entities.Favourite
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinksDao {
    //Network Drinks
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDrink(drink: DatabaseDrink)

    @Query("SELECT * FROM databasedrink WHERE id=:drinkId")
    fun getDrinkById(drinkId: Int): Flow<DatabaseDrink>

    @Query("SELECT * FROM databasedrink WHERE name LIKE :drinkName")
    fun getDrinksByName(drinkName: String): Flow<List<DatabaseDrink>>

//    @Query("SELECT * FROM databasedrink WHERE name LIKE :ingredientName")
//    fun getDrinkByIngredient(ingredientName: String): Flow<List<String>>

    @Query("SELECT * FROM databasedrink WHERE name=:drinkName")
    fun getDrinksListByDrinkName(drinkName: String): Flow<List<DatabaseDrink>>

    //Favourites Section
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavourites(favourite: Favourite)

    @Query("SELECT * FROM favourites_table")
    fun getAllFavouritesIds(): Flow<List<Favourite>>

    @Query("DELETE FROM favourites_table WHERE drinkId=:drinkId")
    fun removeFromFavourites(drinkId: Int)

    //Comments Section
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToCommentaries(commentary: Commentary)

    @Query("SELECT * FROM commentaries_table")
    fun getCommentary(): Flow<List<Commentary>>

    @Query("DELETE FROM commentaries_table WHERE drinkId=:drinkId")
    fun removeCommentary(drinkId: Int)
}