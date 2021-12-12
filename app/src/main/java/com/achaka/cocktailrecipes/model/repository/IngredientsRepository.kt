package com.achaka.cocktailrecipes.model.repository

import android.util.Log
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.DatabaseIngredient
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Ingredient
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.NetworkServiceApi
import com.achaka.cocktailrecipes.model.network.dtos.FullIngredientDetails
import com.achaka.cocktailrecipes.model.network.dtos.IngredientResponse
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.network.networkresponseadapter.NetworkResponse
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.lang.Error
import java.lang.Exception

class IngredientsRepository(private val database: CocktailsAppDatabase) {

    fun getIngredientByNameDatabase(name: String): Flow<DatabaseIngredient?> {
        return database.ingredientsDao().getIngredientByName(name)
    }

    fun insertIngredient(ingredient: DatabaseIngredient) {
        database.ingredientsDao().insertIngredient(ingredient)
    }

    fun getIngredientByName(name: String): Flow<State<Ingredient>?> {
        return getIngredientByNameDatabase(name)
            .map { databaseResult ->
                if (databaseResult != null) {
                    State.Success<Ingredient>(databaseResult.asDomainModel())
                } else {
                    var resp: State<Ingredient>? = null
                    when (val ingredientResponse =
                        NetworkApi.retrofitService.getIngredientByName(name)) {
                        is NetworkResponse.Success<IngredientResponse> -> {
                            val networkIngredient = ingredientResponse.body.response[0]
                            val databaseIngredient = networkIngredient.asDatabaseModel()
                            insertIngredient(databaseIngredient)
                            resp = State.Success(databaseIngredient.asDomainModel())
                            Log.d("Network response success", "success")
                        }
                        is NetworkResponse.ApiError<String> -> {
                            //filter later
//                            ingredientResponse.body
                            resp = State.Error(ingredientResponse.code.toString())
                            Log.d("Network response api error", ingredientResponse.code.toString())
                        }
                        is NetworkResponse.NetworkError -> {
                            resp = State.Error("Network Error")
                            Log.d("Network error", "Network error")
                        }
                        is NetworkResponse.UnknownError -> {
                            resp = State.Error("Unknown Error")
                            Log.d("Unknown Error", "Unknown Error")
                        }
                    }
                    Log.d("RESP", resp.toString())
                    resp
                }
            }
    }

}

class ApiErrorException : Exception() {}