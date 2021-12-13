package com.achaka.cocktailrecipes.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.database.entities.Favourite
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Drink
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

    private val _state = MutableStateFlow<State<Drink>?>(null)
    val state = _state.asStateFlow()

    init {
        getFavourites()
    }

    fun getFavourites() {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                val favDrinks = mutableListOf<Drink>()
                drinkRepository.getFavourites().collect {
                    when (it) {
                        is State.Loading -> {
                            _state.value = State.Loading
                        }
                        is State.Success -> {
                            _state.value = State.Success(it.data)
                            favDrinks.add(it.data)
                            _favouriteDrinks.value = favDrinks
                        }
                        is State.Error -> {
                            Log.d("vm error", "vm error")
                        }
                    }
                }
            }
        }
    }

//    fun getUserFavourites() {
//        viewModelScope.launch {
//            drinkRepository.getAllFavourites().collect { favouriteList ->
//                userDrinkRepository.getUserDrinksById(
//                    favouriteList.filter { it.isUserDrink }
//                        .map { favourite -> favourite.drinkId }).collect {
//                    _favouriteUserDrinks.value = it.map { drink -> drink.asDomainModel() }
//
//                }
//            }
//        }
//    }
}
