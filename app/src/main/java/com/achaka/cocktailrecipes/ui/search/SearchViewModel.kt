package com.achaka.cocktailrecipes.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.data.database.entities.Recent
import com.achaka.cocktailrecipes.data.database.entities.asDomainModel
import com.achaka.cocktailrecipes.ui.util.State
import com.achaka.cocktailrecipes.domain.model.Drink
import com.achaka.cocktailrecipes.data.repository.DrinkRepositoryImpl
import com.achaka.cocktailrecipes.data.repository.SearchRepository
import com.achaka.cocktailrecipes.domain.repository.RandomRepository
import com.achaka.cocktailrecipes.domain.repository.RecentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: DrinkRepositoryImpl,
    private val searchRepository: SearchRepository,
    private val randomRepository: RandomRepository,
    private val recentsRepository: RecentsRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _randomDrinksState = MutableStateFlow<State<List<Drink>>?>(null)
    val randomDrinksState = _randomDrinksState.asStateFlow()

    private val _recentDrinksState = MutableStateFlow<List<Drink>?>(null)
    val recentDrinksState = _recentDrinksState.asStateFlow()

    private val _state = MutableStateFlow<State<List<Drink>>?>(null)
    val state = _state.asStateFlow()

    val popularDrinksSubject: PublishSubject<List<Drink>> = PublishSubject.create()

    private val _queryParams =
        MutableStateFlow(QueryParams(SearchType.DRINK_BY_DRINK_NAME, ""))
    val queryParams = _queryParams.asStateFlow()

    init {
        getRandomDrinks()
        getRecentDrinks()
        getPopularDrinks()
    }

    private fun getRandomDrinks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                randomRepository.getRandomDrinks().collect {
                    if (it is State.Success) {
                        Log.d("RANDOM", it.data.toString())
                        _randomDrinksState.value = it
                    }
                }
            }
        }
    }

    private fun getRecentDrinks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                recentsRepository.getAllRecents().collect { recentList ->
                    val ids = recentList.map { it.drinkId }
                    val drinks = repository.getDrinksById(ids)?.map { it.asDomainModel() }
                    _recentDrinksState.value = drinks
                }
            }
        }
    }

//    private fun getDrinksById(ids: List<Int>): List<Drink> {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//
//            }
//        }
//    }

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

    private fun searchDrinkByName(query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                searchRepository.searchDrinkByName(query).collect {
                    _state.value = it
                }
            }
        }

    }

    private fun searchDrinkByIngredientName(query: String) {
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