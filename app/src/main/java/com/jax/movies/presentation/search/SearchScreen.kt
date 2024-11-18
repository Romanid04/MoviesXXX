package com.jax.movies.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.jax.movies.model.UIState

@Composable
fun SearchScreen(paddingValues: PaddingValues,
                 uiState: UIState
) {
    when(uiState){
        is UIState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(paddingValues))
        }
        is UIState.Success -> {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Search Screen")
            }
        }
        is UIState.Error -> {
            Text("Error fetching",
                color  = Color.Red)
        }
        is UIState.Initial -> {
            Text("Welcome!",
                color = Color.Green)
        }
    }
}