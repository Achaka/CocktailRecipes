package com.achaka.cocktailrecipes.data.repository

import android.util.Log
import com.achaka.cocktailrecipes.data.database.entities.asDomainModel
import com.achaka.cocktailrecipes.data.network.NetworkServiceApi
import com.achaka.cocktailrecipes.data.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.data.network.networkresponseadapter.NetworkResponse
import com.achaka.cocktailrecipes.domain.model.Drink
import com.achaka.cocktailrecipes.domain.repository.RandomRepository
import com.achaka.cocktailrecipes.ui.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RandomRepositoryImpl @Inject constructor(
    private val networkApi: NetworkServiceApi
) : RandomRepository {

    override suspend fun getRandomDrinks(): Flow<State<List<Drink>>> = flow {

        when (val networkResponse = networkApi.getTenRandomCocktails()) {
            is NetworkResponse.Success -> {
                emit(
                    State.Success(
                        networkResponse.body.response.map { it.asDatabaseModel().asDomainModel() }
                    )
                )
            }
        }
    }
}