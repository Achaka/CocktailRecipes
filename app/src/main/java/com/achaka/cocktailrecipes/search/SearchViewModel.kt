package com.achaka.cocktailrecipes.search

import androidx.lifecycle.ViewModel
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.io.IOException

class SearchViewModel(private val repository: DrinkRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val randomDrinksSubject: BehaviorSubject<List<Drink>> = BehaviorSubject.create()
    val popularDrinksSubject: PublishSubject<List<Drink>> = PublishSubject.create()
    val recentDrinksSubject: BehaviorSubject<List<Drink>> = BehaviorSubject.create()

    init {
        getRandomDrinks()
        getPopularDrinks()
//        getRecentDrinks()
    }

    private fun getRandomDrinks() {
        val disposable = repository.getTenRandomCocktails()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    randomDrinksSubject.onNext(it)
                }, {
                    randomDrinksSubject.onError(IOException())
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun getPopularDrinks() {
        val disposable = repository.getPopularCocktails()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    popularDrinksSubject.onNext(it)
                }, {
                    popularDrinksSubject.onError(IOException())
                }
            )
        compositeDisposable.add(disposable)
    }

//    fun getRecentDrinks() {
//
//    }

//    private fun getRecentDrinks() {
//        //simply insert into db and fetch
//    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}