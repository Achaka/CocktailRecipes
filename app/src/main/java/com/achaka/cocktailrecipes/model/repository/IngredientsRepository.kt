package com.achaka.cocktailrecipes.model.repository

import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.DatabaseIngredient
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Ingredient
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.NetworkServiceApi
import com.achaka.cocktailrecipes.model.network.dtos.IngredientResponse
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.network.networkresponseadapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IngredientsRepository(private val database: CocktailsAppDatabase) {

    fun getIngredientByNameDatabase(name: String): Flow<Ingredient?> {
        return database.ingredientsDao().getIngredientByName(name).map{ if (it != null) it.asDomainModel() else null }
    }

    fun insertIngredient(ingredient: DatabaseIngredient) {
        database.ingredientsDao().insertIngredient(ingredient)
    }


    suspend fun getIngredientByName(name: String): Flow<IngredientResponse> {
        val result: Ingredient
        val databaseResult = database.ingredientsDao().getIngredientByName(name)
        if (databaseResult != null) {
            result = databaseResult.map { if (it != null) it.asDomainModel() else null }
        } else {
        val  ingredient = NetworkApi.retrofitService.getIngredientByName(name)
            if (ingredient is NetworkResponse.Success<IngredientResponse>) {
                insertIngredient(ingredient.body.response[0].asDatabaseModel())
                result = getIngredientByNameDatabase(name)
            } else result = null
        }
        return result
    }
}