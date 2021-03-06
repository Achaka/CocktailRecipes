package com.achaka.cocktailrecipes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.achaka.cocktailrecipes.data.database.daos.DrinksDao
import com.achaka.cocktailrecipes.data.database.daos.IngredientsDao
import com.achaka.cocktailrecipes.data.database.daos.RecentsDao
import com.achaka.cocktailrecipes.data.database.daos.UserDrinksDao
import com.achaka.cocktailrecipes.data.database.entities.*

@Database(
    entities = [DatabaseDrink::class, DatabaseIngredient::class,
        DatabaseUserDrink::class, Favourite::class, Commentary::class, Recent::class],
    version = 7,
    exportSchema = false
)
@TypeConverters(StringToListConverter::class)
abstract class CocktailsAppDatabase : RoomDatabase() {

    abstract fun userDrinksDao(): UserDrinksDao
    abstract fun drinksDao(): DrinksDao
    abstract fun ingredientsDao(): IngredientsDao
    abstract fun recentsDao(): RecentsDao

    companion object {
        @Volatile
        private var INSTANCE: CocktailsAppDatabase? = null

        fun getDatabase(context: Context): CocktailsAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CocktailsAppDatabase::class.java,
                    "cocktail_recipes_database"
                ).fallbackToDestructiveMigration()
                 .build()
                INSTANCE = instance
                instance
            }
        }
    }
}