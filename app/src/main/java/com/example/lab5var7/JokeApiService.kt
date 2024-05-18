package com.example.lab5var7

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Retrofit service interface for fetching jokes from the API.
 */
interface JokeApiService {
    /**
     * Fetch a random joke.
     *
     * @return A [Joke] object.
     */
    @GET("random_joke")
    suspend fun getRandomJoke(): Joke
}

/**
 * Singleton object to create and provide the Retrofit service for jokes.
 */
object JokeApi {
    private const val BASE_URL = "https://official-joke-api.appspot.com/"
    /**
     * The Retrofit service instance for fetching jokes.
     */
    val retrofitService: JokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JokeApiService::class.java)
    }
}

