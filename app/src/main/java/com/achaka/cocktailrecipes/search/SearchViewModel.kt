package com.achaka.cocktailrecipes.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.State
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import com.achaka.cocktailrecipes.model.repository.SearchRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class SearchViewModel(
    private val repository: DrinkRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val randomDrinksSubject: BehaviorSubject<List<Drink>> = BehaviorSubject.create()
    val popularDrinksSubject: PublishSubject<List<Drink>> = PublishSubject.create()
    val recentDrinksSubject: BehaviorSubject<List<Drink>> = BehaviorSubject.create()

    private val _state = MutableStateFlow<State<List<Drink>>?>(null)
    val state = _state.asStateFlow()

    private val _queryParams =
        MutableStateFlow<QueryParams>(QueryParams(SearchType.DRINK_BY_DRINK_NAME, ""))
    val queryParams = _queryParams.asStateFlow()

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

    fun searchDrinkByIngredientName(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                searchRepository.searchDrinkByIngredientName(query).collect {
                    if (it is State.Success) {
                        _state.value = it
                    }
                }
            }
        }
    }

    fun searchDrink(query: String, queryParams: QueryParams) {
        when (queryParams.searchType) {
            SearchType.DRINK_BY_DRINK_NAME -> {
                searchDrinkByName(query)
            }
            SearchType.DRINK_BY_INGREDIENT_NAME -> {
                searchDrinkByIngredientName(query)
            }
        }
    }

    fun setQueryParams(queryParams: QueryParams) {
        _queryParams.value = queryParams
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}