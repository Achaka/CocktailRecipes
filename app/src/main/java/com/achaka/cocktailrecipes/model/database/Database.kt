package com.achaka.cocktailrecipes.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.achaka.cocktailrecipes.model.database.daos.DrinksDao
import com.achaka.cocktailrecipes.model.database.daos.IngredientsDao
import com.achaka.cocktailrecipes.model.database.entities.DatabaseIngredient

@Database(
    entities = [DatabaseDrink::class, DatabaseIngredient::class],
    version = 1
) @TypeConverters(StringToListConverter::class)
public abstract class CocktailsAppDatabase : RoomDatabase() {

    abstract fun drinksDao(): DrinksDao
    abstract fun ingredientsDao(): IngredientsDao

    companion object {
        @Volatile
        private var INSTANCE: CocktailsAppDatabase? = null

        fun getDatabase(context: Context) : CocktailsAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CocktailsAppDatabase::class.java,
                    "cocktail_recipes_database"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}