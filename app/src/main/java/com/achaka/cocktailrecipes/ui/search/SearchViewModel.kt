package com.achaka.cocktailrecipes.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.ui.util.State
import com.achaka.cocktailrecipes.domain.model.Drink
import com.achaka.cocktailrecipes.data.repository.DrinkRepositoryImpl
import com.achaka.cocktailrecipes.data.repository.SearchRepository
import com.achaka.cocktailrecipes.domain.repository.RandomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: DrinkRepositoryImpl,
    private val searchRepository: SearchRepository,
    private val randomRepository: RandomRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _randomDrinksState = MutableStateFlow<State<List<Drink>>?>(null)
    val randomDrinksState = _randomDrinksState.asStateFlow()

    val popularDrinksSubject: PublishSubject<List<Drink>> = PublishSubject.create()
    val recentDrinksSubject: BehaviorSubject<List<Drink>> = BehaviorSubject.create()

    private val _state = MutableStateFlow<State<List<Drink>>?>(null)
    val state = _state.asStateFlow()

    private val _queryParams =
        MutableStateFlow(QueryParams(SearchType.DRINK_BY_DRINK_NAME, ""))
    val queryParams = _queryParams.asStateFlow()

    init {
        getRandomDrinks()
        getPopularDrinks()
    }

    private fun getRandomDrinks() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                randomRepository.getRandomDrinks().collect {
                    if (it is State.Success) {
                        _randomDrinksState.value = it
                    }
                }
            }
        }
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