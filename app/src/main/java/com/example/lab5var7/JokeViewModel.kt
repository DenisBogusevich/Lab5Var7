package com.example.lab5var7

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
/**
 * ViewModel class to manage UI-related data in a lifecycle-conscious way.
 * Uses the AndroidViewModel class to have access to the application context.
 *
 * @param application The application context.
 */
class JokeViewModel(application: Application) : AndroidViewModel(application) {
    private val jokeDatabaseHelper = JokeDatabaseHelper(application)

    private val _currentJoke = MutableStateFlow<Joke?>(null)
    val currentJoke: StateFlow<Joke?> get() = _currentJoke

    private val _history = MutableStateFlow<List<Joke>>(emptyList())
    val history: StateFlow<List<Joke>> get() = _history

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _history.value = jokeDatabaseHelper.getAllJokes()
        }
    }

    fun fetchNewJoke() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newJoke = JokeApi.retrofitService.getRandomJoke()
                jokeDatabaseHelper.insertJoke(newJoke)
                _currentJoke.value = newJoke
                _history.value = jokeDatabaseHelper.getAllJokes()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun selectJokeFromHistory(joke: Joke) {
        _currentJoke.value = joke
    }

    fun clearAllJokes() {
        viewModelScope.launch(Dispatchers.IO) {
            jokeDatabaseHelper.clearAllJokes()
            _history.value = jokeDatabaseHelper.getAllJokes()
        }
    }
}
