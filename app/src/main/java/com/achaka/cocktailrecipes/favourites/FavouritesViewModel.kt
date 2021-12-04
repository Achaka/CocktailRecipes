package com.achaka.cocktailrecipes.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.model.database.entities.Favourite
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FavouritesViewModel(
    private val drinkRepository: DrinkRepository,
    private val userDrinkRepository: UserDrinkRepository
) : ViewModel() {

    private val ioDispatcher = Dispatchers.IO

    private var _favouriteDrinks = MutableStateFlow<List<DrinkItem>>(emptyList())
    val favouriteDrinks: StateFlow<List<DrinkItem>> = _favouriteDrinks.asStateFlow()

    private var _favouriteUserDrinks = MutableStateFlow<List<DrinkItem>>(emptyList())
    val favouriteUserDrinks: StateFlow<List<DrinkItem>> = _favouriteUserDrinks.asStateFlow()

    init {
        getFavourites()
        getUserFavourites()
    }

    fun getFavourites() {
        viewModelScope.launch {
            drinkRepository.getAllFavourites().collect { favouriteList ->
                drinkRepository.getDrinksById(
                    favouriteList.filter { !it.isUserDrink }
                        .map { favourite -> favourite.drinkId }
                ).collect {
                    if (it.isNullOrEmpty()) {
                        favouriteList.forEach {
                            withContext(ioDispatcher) {
                                val networkResponse =
                                    NetworkApi.retrofitService.getCocktailDetailsById(it.drinkId).response[0].asDatabaseModel()
                                drinkRepository.insertDrink(networkResponse)
                            }
                        }
                    } else _favouriteDrinks.value = it.map { drink -> drink.asDomainModel() }
                }
            }
        }
    }

    fun getUserFavourites() {
        viewModelScope.launch {
            drinkRepository.getAllFavourites().collect { favouriteList ->
                userDrinkRepository.getUserDrinksById(
                    favouriteList.filter { it.isUserDrink }
                        .map { favourite -> favourite.drinkId }).collect {
                    _favouriteUserDrinks.value = it.map { drink -> drink.asDomainModel() }

                }
            }
        }
    }
}
