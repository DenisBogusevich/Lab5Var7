package com.example.lab5var7

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface JokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Joke)

    @Query("SELECT * FROM joke")
    fun getAllJokes(): kotlinx.coroutines.flow.Flow<List<Joke>>

    @Query("DELETE FROM joke")
    suspend fun clearAllJokes()
}