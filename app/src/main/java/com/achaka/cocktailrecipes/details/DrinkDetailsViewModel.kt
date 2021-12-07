package com.achaka.cocktailrecipes.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achaka.cocktailrecipes.model.database.entities.Commentary
import com.achaka.cocktailrecipes.model.database.entities.Recent
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.domain.DrinkItem
import com.achaka.cocktailrecipes.model.domain.IngredientMeasureItem
import com.achaka.cocktailrecipes.model.repository.DrinkRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DrinkDetailsViewModel(private val repository: DrinkRepository) : ViewModel() {

    private val capacity = 3

    private val scope = viewModelScope
    private val ioDispatcher = Dispatchers.IO

    private val _isFavourite = MutableStateFlow<Boolean>(false)
    val isFavourite = _isFavourite.asStateFlow()

    //add to favourites
    fun addToFavourites(drinkItem: DrinkItem?) {
        scope.launch {
            if (drinkItem != null) {
                withContext(ioDispatcher) {
                    repository.addToFavourites(drinkItem)
                }
            }
        }
    }

    fun ifInFavourites(drinkItem: DrinkItem?) {
        scope.launch {
            val ids = mutableListOf<Int>()
            withContext(ioDispatcher) {
                repository.getAllFavourites().collect { favouriteList ->
                    ids.addAll(favouriteList.map { it.drinkId })
                    _isFavourite.value = ids.contains((drinkItem as Drink).id)
                }
            }
        }
    }

    fun addToRecent(drinkId: Int) {
        val recent = Recent(drinkId, System.currentTimeMillis())
        repository.getRecentDrinks()
            .subscribeOn(Schedulers.io())
            .subscribe({ recentItems ->
                if (!recentItems.any { it.drinkId == recent.drinkId }) {
                    if ((recentItems.size >= capacity)) {
                        val sortedList =
                            recentItems.sortedByDescending { recent ->
                                recent.timestamp
                            }
                        val lastItemTimestamp = sortedList[capacity - 1].timestamp
                        repository.removeRecentItem(lastItemTimestamp).subscribe()
                    }
                    repository.insertRecentItem(recent).subscribe()
                }
            },
                {
                    //TODO later
                })
    }


    fun addCommentary(commentary: Commentary) {

    }

    //count total abv
    fun countTotalAbv(list: List<IngredientMeasureItem>) {

    }

    fun removeFromFavourites(drinkItem: DrinkItem?) {
        scope.launch {
            withContext(ioDispatcher) {
                drinkItem as Drink
                repository.removeFromFavourites(drinkItem.id)
                repository.deleteDrinkById(drinkItem.id)
            }
            ifInFavourites(drinkItem)
        }
    }
}