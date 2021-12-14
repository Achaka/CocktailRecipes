package com.achaka.cocktailrecipes.model.repository

import android.util.Log
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.network.networkresponseadapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SearchRepository(database: CocktailsAppDatabase) {

    suspend fun searchDrinkByName(query: String): Flow<State<List<Drink>>> = flow {
        val networkResponse = NetworkApi.retrofitService.getCocktailsByName(query)
        when (networkResponse) {
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
}