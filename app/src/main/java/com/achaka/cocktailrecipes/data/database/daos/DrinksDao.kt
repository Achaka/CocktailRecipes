package com.achaka.cocktailrecipes.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.achaka.cocktailrecipes.data.database.entities.Commentary
import com.achaka.cocktailrecipes.data.database.entities.DatabaseDrink
import com.achaka.cocktailrecipes.data.database.entities.Favourite
import com.achaka.cocktailrecipes.data.database.entities.Recent
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinksDao {
    //Network Drinks
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDrink(drink: DatabaseDrink)

    @Query("SELECT * FROM databasedrink WHERE id=:drinkId")
    fun getDrinkById(drinkId: Int): DatabaseDrink?

    @Query("SELECT * FROM databasedrink WHERE id=:drinkId")
    fun getDrinkByIdRx(drinkId: Int): Single<DatabaseDrink>

    @Query("SELECT * FROM databasedrink WHERE id IN (:drinkIds)")
    fun getDrinksById(drinkIds: List<Int>): List<DatabaseDrink>

    @Query("SELECT * FROM databasedrink WHERE name LIKE :drinkName")
    fun getDrinksByName(drinkName: String): Flow<List<DatabaseDrink>>

//    @Query("SELECT * FROM databasedrink WHERE name LIKE '%' :ingredientName")
//    fun getDrinkByIngredient(ingredientName: String): Flow<List<String>>

    @Query("SELECT * FROM databasedrink WHERE name=:drinkName")
    fun getDrinksListByDrinkName(drinkName: String): Flow<List<DatabaseDrink>>

    @Query("DELETE FROM databasedrink WHERE id=:drinkId")
    fun deleteDrinkById(drinkId: Int)

    //Favourites Section
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFavourites(favourite: Favourite)

    @Query("SELECT * FROM favourites_table")
    fun getFavouriteIds(): List<Favourite>

    @Query("SELECT * FROM favourites_table")
    fun getFavouriteIdsFlow(): Flow<List<Favourite>>

    @Query("DELETE FROM favourites_table WHERE drinkId=:drinkId")
    fun removeFromFavourites(drinkId: Int)

    //Comments Section
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCommentary(commentary: Commentary)

    @Query("SELECT * FROM commentaries_table")
    fun getCommentaries(): Flow<List<Commentary>>

    @Query("SELECT * FROM commentaries_table WHERE drinkId=:drinkId")
    fun getCommentary(drinkId: Int): Flow<Commentary>

    @Query("DELETE FROM commentaries_table WHERE drinkId=:drinkId")
    fun removeCommentary(drinkId: Int)

    //Recent section

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentItem(recent: Recent): Single<Long>

    @Query("SELECT * FROM recent_table")
    fun getRecentItems(): Observable<List<Recent>>

    @Query("DELETE FROM recent_table WHERE timestamp=:timestamp")
    fun removeRecentItem(timestamp: Long): Completable
}