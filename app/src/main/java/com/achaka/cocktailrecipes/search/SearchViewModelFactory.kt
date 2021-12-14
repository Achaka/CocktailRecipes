package com.achaka.cocktailrecipes.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import com.achaka.cocktailrecipes.model.repository.SearchRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val drinkRepository: DrinkRepository,
    private val searchRepository: SearchRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(drinkRepository, searchRepository) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}