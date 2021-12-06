package com.achaka.cocktailrecipes.search

import androidx.lifecycle.ViewModel
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class SearchViewModel(private val repository: DrinkRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val randomDrinks: BehaviorSubject<List<Drink>> = BehaviorSubject.create()
    val popularDrinks: PublishSubject<List<Drink>> = PublishSubject.create()

    init {
        getRandomDrinks()
        getPopularDrinks()
    }

    private fun getRandomDrinks() {
        val disposable = repository.getTenRandomCocktails()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value -> randomDrinks.onNext(value.asDatabaseModel().asDomainModel()) }, {}
            )
        compositeDisposable.add(disposable)
    }

    private fun getPopularDrinks() {
        val disposable = repository.getPopularCocktails()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { value -> popularDrinks.onNext(value.asDatabaseModel().asDomainModel()) }, {}
            )
        compositeDisposable.add(disposable)
    }

    //TODO Not yet implemented
    fun getRecentDrinks() {

    }
}