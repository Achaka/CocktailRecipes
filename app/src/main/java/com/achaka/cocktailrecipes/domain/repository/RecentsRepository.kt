package com.achaka.cocktailrecipes.domain.repository

import com.achaka.cocktailrecipes.data.database.entities.Recent
import kotlinx.coroutines.flow.Flow

interface RecentsRepository {

    fun addToRecents(recent: Recent)

    fun getAllRecents(): Flow<List<Recent>>

    fun removeFromRecents()
}