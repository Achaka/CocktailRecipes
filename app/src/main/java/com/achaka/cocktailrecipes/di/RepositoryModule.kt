package com.achaka.cocktailrecipes.di

import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.network.NetworkServiceApi
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import com.achaka.cocktailrecipes.model.repository.IngredientsRepository
import com.achaka.cocktailrecipes.model.repository.SearchRepository
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideIngredientRepository(
        database: CocktailsAppDatabase,
        networkApi: NetworkServiceApi
    ): IngredientsRepository =
        IngredientsRepository(database, networkApi)


    @Provides
    @Singleton
    fun provideDrinkRepository(
        database: CocktailsAppDatabase,
        networkApi: NetworkServiceApi
    ): DrinkRepository =
        DrinkRepository(database, networkApi)

    @Provides
    @Singleton
    fun provideSearchRepository(
        database: CocktailsAppDatabase,
        networkApi: NetworkServiceApi
    ): SearchRepository =
        SearchRepository(database, networkApi)

    @Provides
    @Singleton
    fun provideUserDrinkRepository(
        database: CocktailsAppDatabase
    ): UserDrinkRepository = UserDrinkRepository(database)
}