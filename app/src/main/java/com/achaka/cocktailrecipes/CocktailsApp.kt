package com.achaka.cocktailrecipes

import android.app.Application
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.repository.UserDrinkRepository
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import com.achaka.cocktailrecipes.model.repository.SearchRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CocktailsApp: Application()