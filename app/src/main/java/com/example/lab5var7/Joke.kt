package com.example.lab5var7

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "joke")
data class Joke(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val setup: String,
    val punchline: String
)