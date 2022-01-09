package com.achaka.cocktailrecipes.di

import com.achaka.cocktailrecipes.data.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.data.network.NetworkServiceApi
import com.achaka.cocktailrecipes.data.repository.*
import com.achaka.cocktailrecipes.domain.repository.RandomRepository
import com.achaka.cocktailrecipes.domain.repository.RecentsRepository
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
    ): DrinkRepositoryImpl =
        DrinkRepositoryImpl(database, networkApi)

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

    @Provides
    @Singleton
    fun providesRandomRepository(
        networkApi: NetworkServiceApi
    ): RandomRepository = RandomRepositoryImpl(networkApi)

    @Provides
    @Singleton
    fun providesRecentsRepository(
        database: CocktailsAppDatabase
    ): RecentsRepository = RecentsRepositoryImpl(database)
}