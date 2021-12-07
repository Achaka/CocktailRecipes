package com.achaka.cocktailrecipes.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(private val repository: DrinkRepository) : ViewModel() {

    val scope = viewModelScope

    private val compositeDisposable = CompositeDisposable()

    val randomDrinksSubject: BehaviorSubject<List<Drink>> = BehaviorSubject.create()
    val popularDrinksSubject: PublishSubject<List<Drink>> = PublishSubject.create()
    val recentDrinksSubject: BehaviorSubject<List<Drink>> = BehaviorSubject.create()

    init {
        getRandomDrinks()
        getPopularDrinks()
        getRecentDrinks()
    }

    private fun getRandomDrinks() {
        val disposable = repository.getTenRandomCocktails()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value -> randomDrinksSubject.onNext(value.asDatabaseModel().asDomainModel()) }, {}
            )
        compositeDisposable.add(disposable)
    }

    private fun getPopularDrinks() {
        val disposable = repository.getPopularCocktails()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value -> popularDrinksSubject.onNext(value.asDatabaseModel().asDomainModel()) },
                {}
            )
        compositeDisposable.add(disposable)
    }

//    fun getRecentDrinks() {
//
//    }

    private fun getRecentDrinks() {
        //simply insert into db and fetch
    }

}