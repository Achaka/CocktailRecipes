package com.achaka.cocktailrecipes.model.repository

import com.achaka.cocktailrecipes.CocktailsApp
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Ingredient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class IngredientsRepository(private val database: CocktailsAppDatabase) {

    fun getIngredientByName(name: String): Flow<Ingredient?> {
        return database.ingredientsDao().getIngredientByName(name).map{ if (it != null) it.asDomainModel() else null }
    }
}