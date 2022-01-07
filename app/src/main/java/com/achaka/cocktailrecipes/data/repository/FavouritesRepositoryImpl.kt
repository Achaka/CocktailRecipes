package com.achaka.cocktailrecipes.data.repository

class FavouritesRepositoryImpl {
//    : FavouritesRepository {

//    override suspend fun getFavourites(): Flow<State<List<Drink>>> = flow {
//
//        emit(State.Loading)
//
//        val ids = getFavouriteIds().map { it.drinkId }
//
//        if (ids.isNullOrEmpty()) {
//            emit(State.Error("No favourites yet!"))
//        } else {
//            ids.map { drinkId ->
//                val drinkById = getDrinkById(drinkId)
//                if (drinkById == null)
//                    when (val networkResult = fetch(drinkId)) {
//                        is NetworkResponse.Success -> {
//                            insertDrink(networkResult.body.response[0].asDatabaseModel())
//                            val databaseDrink = getDrinkById(drinkId)
//                            if (databaseDrink != null) {
//                                //
//                            } else {
//                                emit(State.Error("Could not load $drinkId"))
//                            }
//                        }
//                        is NetworkResponse.ApiError -> {
//                            emit(State.Error("Error ${networkResult.code}"))
//                        }
//                        is NetworkResponse.NetworkError -> {
//                            emit(State.Error("Could not load $drinkId: Network Error"))
//                        }
//                        is NetworkResponse.UnknownError -> {
//                            emit(State.Error("Could not load $drinkId: Unknown Error"))
//                        }
//                    }
//            }
//            val favouriteIds = getFavouriteIds().map { it.drinkId }
//            val favouriteDrinks = getDrinksById(favouriteIds)
//            if (favouriteDrinks != null) {
//                emit(State.Success(favouriteDrinks.map { it.asDomainModel() }))
//            }
//        }
//    }

//    override fun removeFromFavourites(drinkId: Int) {
//        TODO("Not yet implemented")
//    }
}