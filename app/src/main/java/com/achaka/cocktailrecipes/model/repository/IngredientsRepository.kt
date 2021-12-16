package com.achaka.cocktailrecipes.model.repository

import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.DatabaseIngredient
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Ingredient
import com.achaka.cocktailrecipes.model.network.NetworkServiceApi
import com.achaka.cocktailrecipes.model.network.dtos.IngredientResponse
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.network.networkresponseadapter.NetworkResponse
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class IngredientsRepository @Inject constructor(private val database: CocktailsAppDatabase, private val networkApi: NetworkServiceApi) {

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
                        networkApi.getIngredientByName(name)) {
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