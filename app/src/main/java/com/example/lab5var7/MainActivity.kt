package com.example.lab5var7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * MainActivity class that sets up the navigation and initializes the ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val jokeViewModel: JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController, jokeViewModel) }
                    composable("history") { HistoryScreen(navController, jokeViewModel) }
                }
            }
        }
    }
}
/**
 * Composable function for the main screen, displaying the current joke and buttons for fetching a new joke or viewing history.
 *
 * @param navController The NavController for navigation.
 * @param viewModel The ViewModel to manage joke data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, viewModel: JokeViewModel) {
    val currentJoke by viewModel.currentJoke.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(title = { Text("Joke App") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                currentJoke?.let {
                    Text(text = it.setup, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it.punchline, style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    viewModel.fetchNewJoke()

                }) {
                    Text("New Joke")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { navController.navigate("history") }) {
                    Text("History")
                }
            }
        }
    )
}

/**
 * Composable function for the history screen, displaying the list of previously viewed jokes.
 *
 * @param navController The NavController for navigation.
 * @param viewModel The ViewModel to manage joke data.
 */
@ExperimentalMaterial3Api
@Composable
fun HistoryScreen(navController: NavController, viewModel: JokeViewModel) {
    val history by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Joke History") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                items(history) { joke ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.selectJokeFromHistory(joke)
                                navController.popBackStack()
                            }
                            .padding(8.dp)
                    ) {
                        Text(text = joke.setup, style = MaterialTheme.typography.headlineSmall)
                        Text(text = joke.punchline, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    )
}