package com.achaka.cocktailrecipes.data.repository

import com.achaka.cocktailrecipes.data.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.data.database.entities.Recent
import com.achaka.cocktailrecipes.domain.model.Drink
import com.achaka.cocktailrecipes.domain.repository.RecentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RecentsRepositoryImpl constructor(
    private val database: CocktailsAppDatabase
): RecentsRepository {

    override fun addToRecents(recent: Recent) {
        database.recentsDao().addToRecents(recent)
    }

    override fun getAllRecents(): Flow<List<Recent>> {
        return database.recentsDao().getAllRecents()
    }

    override fun removeFromRecents() {

    }
}