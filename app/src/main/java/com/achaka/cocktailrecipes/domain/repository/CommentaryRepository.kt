package com.achaka.cocktailrecipes.domain.repository

import com.achaka.cocktailrecipes.data.database.entities.Commentary
import kotlinx.coroutines.flow.Flow

interface CommentaryRepository {

    fun addCommentary(commentary: Commentary)

    fun getCommentaries(): Flow<List<Commentary>>

    fun getCommentaryById(drinkId: Int): Flow<Commentary>

    fun removeCommentary(drinkId: Int)

}