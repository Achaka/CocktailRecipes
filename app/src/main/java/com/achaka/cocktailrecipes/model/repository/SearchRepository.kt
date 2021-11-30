package com.achaka.cocktailrecipes.model.repository

import android.util.Log
import com.achaka.cocktailrecipes.model.database.CocktailsAppDatabase
import com.achaka.cocktailrecipes.model.database.asDomainModel
import com.achaka.cocktailrecipes.model.domain.Drink
import com.achaka.cocktailrecipes.model.network.NetworkApi
import com.achaka.cocktailrecipes.model.network.dtos.asDatabaseModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class SearchRepository(private val database: CocktailsAppDatabase) {

    fun getTenRandomCocktails(): Single<List<Drink>> {
//        database.drinksDao()
        return NetworkApi.retrofitService.getTenRandomCocktails().map {
            it.asDatabaseModel().asDomainModel()
        }

//        NetworkApi.retrofitService.getTenRandomCocktails().subscribeOn(Schedulers.io()).subscribe(
//            {
//                //is network available? -> fetch from network -> update cache for 10 random
//                //is no network available? -> get from cache 10 random drinks
//                //analyze response and pass it to vm
//                Log.d("get 10 random", "SUCCESS")
//                val databaseDrinkList = it.asDatabaseModel()
//                    databaseDrinkList.forEach { database.drinksDao().insertDrink(it) }
//                val domainDrinkList = databaseDrinkList.asDomainModel()
//                    observable = Observable.fromIterable(domainDrinkList)
//            },{
//
//            }
//        )
//        return observable
    }

    fun getPopularCocktails(): Single<List<Drink>> {
//        database.drinksDao()
        return NetworkApi.retrofitService.getPopularCocktails()
            .map { it.asDatabaseModel().asDomainModel() }
    }
}