package com.achaka.cocktailrecipes.model.repository

import android.util.Log
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.DatabaseIngredient
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Ingredient
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.IngredientResponse
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.network.networkresponseadapter.NetworkResponse
import kotlinx.coroutines.flow.*
import java.lang.Exception

class IngredientsRepository(private val database: CocktailsAppDatabase) {

    private fun getIngredientByNameDatabase(name: String): Flow<DatabaseIngredient?> {
        return database.ingredientsDao().getIngredientByName(name)
    }

    private fun insertIngredient(ingredient: DatabaseIngredient) {
        database.ingredientsDao().insertIngredient(ingredient)
    }

    fun getIngredientByName(name: String): Flow<State<Ingredient>?> {
        return getIngredientByNameDatabase(name)
            .map { databaseResult ->
                if (databaseResult != null) {
                    State.Success(databaseResult.asDomainModel())
                } else {
                    var resp: State<Ingredient>? = null
                    when (val ingredientResponse =
                        NetworkApi.retrofitService.getIngredientByName(name)) {
                        is NetworkResponse.Success<IngredientResponse> -> {
                            val networkIngredient = ingredientResponse.body.response[0]
                            val databaseIngredient = networkIngredient.asDatabaseModel()
                            insertIngredient(databaseIngredient)
                            resp = State.Success(databaseIngredient.asDomainModel())
                        }
                        is NetworkResponse.ApiError<String> -> {
                            //filter later
//                            ingredientResponse.body
                            resp = State.Error(ingredientResponse.code.toString())
                        }
                        is NetworkResponse.NetworkError -> {
                            resp = State.Error("Network Error")
                        }
                        is NetworkResponse.UnknownError -> {
                            resp = State.Error("Unknown Error")
                        }
                    }
                    resp
                }
            }
    }
}