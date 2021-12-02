package com.achaka.cocktailrecipes.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import java.lang.IllegalArgumentException

class DetailsViewModelFactory(private val repository: DrinkRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DrinkDetailsViewModel::class.java)) {
            return DrinkDetailsViewModel(repository) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}