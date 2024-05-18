package com.example.lab5var7

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel to manage joke data and state.
 */
class JokeViewModel : ViewModel() {
    private val _currentJoke = MutableStateFlow<Joke?>(null)
    val currentJoke: StateFlow<Joke?> get() = _currentJoke

    private val _history = MutableStateFlow<List<Joke>>(emptyList())

    val history: StateFlow<List<Joke>> get() = _history
    /**
     * Fetch a new random joke and update the current joke and history.
     */
    fun fetchNewJoke() {
        viewModelScope.launch {
            try {
                val newJoke = JokeApi.retrofitService.getRandomJoke()
                _currentJoke.value = newJoke
                _history.value += newJoke
                println(history.value)

            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    /**
     * Select a joke from history and set it as the current joke.
     *
     * @param joke The joke to be set as the current joke.
     */
    fun selectJokeFromHistory(joke: Joke) {
        _currentJoke.value = joke
    }
}