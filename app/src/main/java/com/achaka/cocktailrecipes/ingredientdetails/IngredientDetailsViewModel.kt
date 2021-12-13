package com.achaka.cocktailrecipes.ingredientdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Alcoholic
import com.achaka.cocktailrecipes.model.domain.Ingredient
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.IngredientResponse
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.repository.IngredientsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IngredientDetailsViewModel(private val ingredientsRepository: IngredientsRepository) :
    ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _state = MutableStateFlow<State<Ingredient>>(State.Loading)
    val state = _state.asStateFlow()

    private val ioDispatcher = Dispatchers.IO

    fun getIngredientByName(ingredientName: String) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                ingredientsRepository.getIngredientByName(ingredientName).collect {
                    when (it) {
                        is State.Success -> {
                            _state.value = it
                            Log.d("ingredient", "success")
                        }
                        is State.Error -> {
                            _state.value = it
                            Log.d("EXCEPTION viewmodel", it.exceptionMessage)
                        }
                        is State.Loading -> {
                            //do nothing
                        }
                    }
                }
            }
        }
    }
}
