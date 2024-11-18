package com.jax.movies.model

import com.jax.movies.data.Movie
import com.jax.movies.data.Staff

sealed class MovieDetailUIState {
    object Initial : MovieDetailUIState()
    object Loading : MovieDetailUIState()
    data class Success(val movie: Movie, val actors: List<Staff>, val employees: List<Staff>) : MovieDetailUIState()
    data class Error(val message: String) : MovieDetailUIState()
}
