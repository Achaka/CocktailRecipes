package com.achaka.cocktailrecipes.di

import android.app.Application
import androidx.room.Room
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CocktailsAppDatabase =
        Room.databaseBuilder(
            app,
            CocktailsAppDatabase::class.java,
            "cocktail_recipes_database"
        ).fallbackToDestructiveMigration()
            .build()
}