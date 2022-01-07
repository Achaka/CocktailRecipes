package com.achaka.cocktailrecipes.domain.repository

import com.achaka.cocktailrecipes.domain.model.Drink


interface RecentsRepository {

    fun addToRecents(drink: Drink)

    fun getRecents()

    fun removeFromRecents()
}