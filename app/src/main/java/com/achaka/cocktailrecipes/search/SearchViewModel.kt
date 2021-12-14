package com.achaka.cocktailrecipes.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.database.entities.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import com.achaka.cocktailrecipes.model.repository.SearchRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class SearchViewModel(private val repository: DrinkRepository, private val searchRepository: SearchRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val randomDrinksSubject: BehaviorSubject<List<Drink>> = BehaviorSubject.create()
    val popularDrinksSubject: PublishSubject<List<Drink>> = PublishSubject.create()
    val recentDrinksSubject: BehaviorSubject<List<Drink>> = BehaviorSubject.create()

    private val _state = MutableStateFlow<State<List<Drink>>?>(null)
    val state = _state.asStateFlow()

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

    fun searchDrinkByName(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                searchRepository.searchDrinkByName(query).collect {
                    _state.value = it
                }
            }
        }

    }

    private fun searchDrinkByIngredient() {

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