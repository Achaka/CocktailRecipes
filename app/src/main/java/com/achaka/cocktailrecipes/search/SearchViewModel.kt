package com.achaka.cocktailrecipes.search

import androidx.lifecycle.ViewModel
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SearchViewModel(private val repository: DrinkRepository): ViewModel() {

    private val disposables = CompositeDisposable()

    fun getRandomDrinks(): Single<List<Drink>> {
        return repository.getTenRandomCocktails()
    }

    fun getPopularDrinks(): Single<List<Drink>> {
        return repository.getPopularCocktails()
    }

    //TODO Not yet implemented
    fun getRecentDrinks() {

    }
}