package com.achaka.cocktailrecipes.ingredientdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Alcoholic
import com.achaka.cocktailrecipes.model.domain.Ingredient
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.repository.IngredientsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IngredientDetailsViewModel(private val ingredientsRepository: IngredientsRepository) :
    ViewModel() {

    private val _ingredient = MutableStateFlow<Ingredient?>(Ingredient(0, "", "", "", Alcoholic.NON_ALCOHOLIC, ""))
    val ingredient = _ingredient.asStateFlow()

    private val dispatcher = Dispatchers.IO

    fun getIngredientByName(name: String) {
        viewModelScope.launch {

//                ingredientsRepository.getIngredientByName("vodka").collect{_ingredient.value = it}
                _ingredient.value = NetworkApi.retrofitService.getIngredientByName("vodka").response[0].asDatabaseModel().asDomainModel()
        }
    }
}
