package com.achaka.cocktailrecipes.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.achaka.cocktailrecipes.data.database.entities.Recent
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentsDao {
    @Insert
    fun addToRecents(recent: Recent)

    @Query("SELECT * FROM recent_table")
    fun getAllRecents(): Flow<List<Recent>>

    @Query("DELETE FROM recent_table WHERE timestamp = (SELECT MIN(timestamp) FROM recent_table)")
    fun removeFromRecents()
}