package com.achaka.cocktailrecipes.model.repository

import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.DatabaseIngredient
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Ingredient
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.NetworkServiceApi
import com.achaka.cocktailrecipes.model.network.dtos.IngredientResponse
import com.achaka.cocktailrecipes.model.network.networkresponseadapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IngredientsRepository(private val database: CocktailsAppDatabase) {

//    fun getIngredientByName(name: String): Flow<Ingredient?> {
//        return database.ingredientsDao().getIngredientByName(name).map{ if (it != null) it.asDomainModel() else null }
//    }

    fun insertIngredient(ingredient: DatabaseIngredient) {
        database.ingredientsDao().insertIngredient(ingredient)
    }

    suspend fun getIngredientByName(name: String): NetworkResponse<IngredientResponse, String> {
        return NetworkApi.retrofitService.getIngredientByName(name)
    }
}