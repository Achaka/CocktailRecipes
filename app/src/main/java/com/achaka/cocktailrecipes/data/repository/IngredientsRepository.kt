package com.achaka.cocktailrecipes.data.repository

import com.achaka.cocktailrecipes.ui.util.State
import com.achaka.cocktailrecipes.data.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.data.database.entities.DatabaseIngredient
import com.achaka.cocktailrecipes.data.database.entities.asDomainModel
import com.achaka.cocktailrecipes.domain.model.Ingredient
import com.achaka.cocktailrecipes.data.network.NetworkServiceApi
import com.achaka.cocktailrecipes.data.network.dtos.IngredientResponse
import com.achaka.cocktailrecipes.data.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.data.network.networkresponseadapter.NetworkResponse
import com.achaka.cocktailrecipes.domain.repository.IngredientsRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class IngredientsRepository @Inject constructor(
    private val database: CocktailsAppDatabase,
    private val networkApi: NetworkServiceApi
): IngredientsRepository {

    private fun getIngredientByNameDatabase(name: String): Flow<DatabaseIngredient?> {
        return database.ingredientsDao().getIngredientByName(name)
    }

    private fun insertIngredient(ingredient: DatabaseIngredient) {
        database.ingredientsDao().insertIngredient(ingredient)
    }

    override fun getIngredientByName(name: String): Flow<State<Ingredient>?> {
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