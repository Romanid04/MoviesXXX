package com.jax.movies.model

import com.jax.movies.data.Film
import com.jax.movies.data.Movie

sealed class SearchUiState {
    object Initial: SearchUiState()
    object Loading: SearchUiState()
    data class Success(val searchResults: List<Film>): SearchUiState()
    data class Error(val message: String): SearchUiState()
}