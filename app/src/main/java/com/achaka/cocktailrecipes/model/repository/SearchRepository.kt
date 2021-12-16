package com.achaka.cocktailrecipes.model.repository

import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.network.NetworkServiceApi
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.network.networkresponseadapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SearchRepository @Inject constructor(private val database: CocktailsAppDatabase, private val networkApi: NetworkServiceApi) {

    suspend fun searchDrinkByName(query: String): Flow<State<List<Drink>>> = flow {
        when (val networkResponse = networkApi.getCocktailsByName(query)) {
            is NetworkResponse.Success -> {
                emit(State.Success(networkResponse.body.response.map {
                    it.asDatabaseModel().asDomainModel()
                }))
            }
            is NetworkResponse.ApiError -> {
                emit(State.Error("${networkResponse.body} + ${networkResponse.code}"))
            }
            is NetworkResponse.NetworkError -> {
                emit(State.Error(networkResponse.error))
            }
            is NetworkResponse.UnknownError -> {
                emit(State.Error("No drinks found!"))
            }
        }
    }

    suspend fun searchDrinkByIngredientName(query: String): Flow<State<List<Drink>>> = flow {
        when (val networkResponse =
            networkApi.getCocktailsByIngredientName(query)) {
            is NetworkResponse.Success -> {
                val resultIds = networkResponse.body.response.map { it.id }
                if (resultIds.isNotEmpty()) {
                    val drinksList = mutableListOf<Drink>()
                    resultIds.forEach { drinkId ->
                        val dbDrink = database.drinksDao().getDrinkById(drinkId)
                        if (dbDrink != null) {
                            drinksList.add(dbDrink.asDomainModel())
                        } else {
                            val drinkFromNetworkResponse =
                                networkApi.getCocktailDetailsById(drinkId)
                            when (drinkFromNetworkResponse) {
                                is NetworkResponse.Success -> {
                                    drinksList.add(
                                        drinkFromNetworkResponse.body.response[0].asDatabaseModel()
                                            .asDomainModel()
                                    )
                                }
                                is NetworkResponse.ApiError -> {
                                    emit(
                                        State.Error(
                                            "${drinkFromNetworkResponse.body} + " +
                                                    "${drinkFromNetworkResponse.code}"
                                        )
                                    )
                                }
                                is NetworkResponse.NetworkError -> {
                                    emit(State.Error(drinkFromNetworkResponse.error))
                                }
                                is NetworkResponse.UnknownError -> {
                                    emit(State.Error("No drinks found!"))
                                }
                            }
                        }
                    }
                    emit(State.Success(drinksList))
                }
            }
            is NetworkResponse.ApiError -> {
                emit(State.Error("${networkResponse.body} + ${networkResponse.code}"))
            }
            is NetworkResponse.NetworkError -> {
                emit(State.Error(networkResponse.error))
            }
            is NetworkResponse.UnknownError -> {
                emit(State.Error("No drinks found!"))
            }
        }
    }
}