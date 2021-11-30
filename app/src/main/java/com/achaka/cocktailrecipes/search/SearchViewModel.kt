package com.achaka.cocktailrecipes.search

import android.util.Log
import androidx.lifecycle.ViewModel
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.repository.SearchRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchViewModel(private val repository: SearchRepository): ViewModel() {

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