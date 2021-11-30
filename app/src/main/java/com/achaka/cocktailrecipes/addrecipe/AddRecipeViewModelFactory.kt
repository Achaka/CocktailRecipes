package com.achaka.cocktailrecipes.addrecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.cocktailrecipes.model.repository.AddRecipeRepository
import java.lang.IllegalArgumentException

class AddRecipeViewModelFactory(private val repository: AddRecipeRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddRecipeViewModel::class.java)) {
            return AddRecipeViewModel(repository) as T
        }
        else throw IllegalArgumentException()
    }
}