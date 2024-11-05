package com.jax.movies

import android.annotation.SuppressLint
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jax.movies.navigation.HomeNavGraph
import com.jax.movies.presentation.main.MoviesMainScreen
import com.jax.movies.ui.theme.MoviesTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
            MoviesMainScreen()
            }
        }
    }
}


