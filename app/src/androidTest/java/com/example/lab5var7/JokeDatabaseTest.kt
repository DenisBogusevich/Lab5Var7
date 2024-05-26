package com.example.lab5var7

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class JokeDatabaseHelperTest {

    private lateinit var dbHelper: JokeDatabaseHelper

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = JokeDatabaseHelper(context)
    }

    @After
    fun tearDown() {
        dbHelper.clearAllJokes()
    }

    @Test
    fun insertJoke_success() {
        val joke = Joke(
            id = 1,
            type = "general",
            setup = "Why did the chicken cross the road?",
            punchline = "To get to the other side."
        )
        dbHelper.insertJoke(joke)
        val jokes = dbHelper.getAllJokes()


        assertEquals(joke.punchline, jokes[0].punchline)
        assertEquals(joke.setup, jokes[0].setup)
        assertEquals(joke.type, jokes[0].type)

    }

    @Test(expected = Exception::class)
    fun insertJoke_existed() {
        val joke = Joke(
            id = 1,
            type = "general",
            setup = "Why did the chicken cross the road?",
            punchline = "To get to the other side."
        )
        dbHelper.insertJoke(joke)
        dbHelper.insertJoke(joke)

    }


    @Test
    fun clearAllJokes_success() {
        val joke1 = Joke(
            id = 1,
            type = "general",
            setup = "Why did the chicken cross the road?",
            punchline = "To get to the other side."
        )
        val joke2 = Joke(
            id = 2,
            type = "general",
            setup = "What do you call cheese that isn't yours?",
            punchline = "Nacho cheese."
        )
        dbHelper.insertJoke(joke1)
        dbHelper.insertJoke(joke2)
        dbHelper.clearAllJokes()
        val jokes = dbHelper.getAllJokes()
        assertTrue(jokes.isEmpty())
    }

    @Test
    fun clearAllJokes_noJokesToClear() {
        val jokesBeforeClear = dbHelper.getAllJokes()
        dbHelper.clearAllJokes()
        val jokesAfterClear = dbHelper.getAllJokes()
        assertEquals(jokesBeforeClear, jokesAfterClear)
    }
}
