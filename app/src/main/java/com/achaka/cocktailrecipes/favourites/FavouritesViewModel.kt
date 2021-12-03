package com.achaka.cocktailrecipes.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.model.database.entities.Favourite
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class FavouritesViewModel(
    private val drinkRepository: DrinkRepository,
    private val userDrinkRepository: UserDrinkRepository
) : ViewModel() {

    private val dispatcherIO = Dispatchers.IO

    private var _favourites = MutableStateFlow<List<Favourite>>(emptyList())
    val favourites: StateFlow<List<Favourite>> = _favourites.asStateFlow()

    init {
        getFavourites()
        getUserFavourites()
    }

    private var _favouriteDrinks = MutableStateFlow<List<DrinkItem>>(emptyList())
    val favouriteDrinks: StateFlow<List<DrinkItem>> = _favouriteDrinks.asStateFlow()

    private var _favouriteUserDrinks = MutableStateFlow<List<DrinkItem>>(emptyList())
    val favouriteUserDrinks: StateFlow<List<DrinkItem>> = _favouriteUserDrinks.asStateFlow()

    fun getFavourites() {
        viewModelScope.launch {
            drinkRepository.getAllFavourites().collect { favouriteList ->
                drinkRepository.getDrinksById(
                    favouriteList.filter { !it.isUserDrink }
                        .map { favourite -> favourite.drinkId }).collect {
                    _favouriteDrinks.value = it.map { drink -> drink.asDomainModel() }
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
