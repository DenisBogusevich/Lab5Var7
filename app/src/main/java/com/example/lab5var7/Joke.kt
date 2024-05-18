package com.example.lab5var7

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a joke.
 *
 * @property id The unique identifier of the joke.
 * @property type The type or category of the joke.
 * @property setup The setup part of the joke.
 * @property punchline The punchline part of the joke.
 */
@Entity(tableName = "joke")
data class Joke(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val setup: String,
    val punchline: String
)