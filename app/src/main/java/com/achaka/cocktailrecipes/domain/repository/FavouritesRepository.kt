package com.achaka.cocktailrecipes.domain.repository

import com.achaka.cocktailrecipes.domain.model.Drink
import com.achaka.cocktailrecipes.ui.util.State
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    suspend fun getFavourites(): Flow<State<List<Drink>>>

    fun removeFromFavourites(drinkId: Int)


}