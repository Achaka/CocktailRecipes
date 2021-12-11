package com.achaka.cocktailrecipes.ingredientdetails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _ingredient =
        MutableStateFlow<Ingredient?>(Ingredient(0, "", "", "", Alcoholic.NON_ALCOHOLIC, ""))
    val ingredient = _ingredient.asStateFlow()

    private val ioDispatcher = Dispatchers.IO

    fun getIngredientByName(ingredientName: String) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                val result = ingredientsRepository.getIngredientByName(ingredientName)
                Log.d("RESULT", result.toString())
            }
        }
    }

//    fun getIngredientByName(ingredientName: String) {
//        viewModelScope.launch {
//            withContext(ioDispatcher) {
//                ingredientsRepository.getIngredientByName(ingredientName).collect {
//                    if (it == null) {
//                        withContext(ioDispatcher) {
//                            val ingredient =
//                                NetworkApi.retrofitService.getIngredientByName(ingredientName).response[0].asDatabaseModel()
//                            ingredientsRepository.insertIngredient(ingredient)
//                            _ingredient.value = ingredient.asDomainModel()
//                        }
//                    } else _ingredient.value = it
//                }
//            }
//        }
//    }
}
