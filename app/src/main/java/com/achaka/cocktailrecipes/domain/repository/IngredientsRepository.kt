package com.achaka.cocktailrecipes.domain.repository

import com.achaka.cocktailrecipes.domain.model.Ingredient
import com.achaka.cocktailrecipes.ui.util.State
import kotlinx.coroutines.flow.Flow

interface IngredientsRepository {

    fun getIngredientByName(name: String): Flow<State<Ingredient>?>

}