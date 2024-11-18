package com.jax.movies.model

import com.jax.movies.data.Movie


sealed class UIState {
    object Initial: UIState()
    data class Success(val movies: List<List<Movie>>): UIState()
    object Error: UIState()
    object Loading: UIState()
}
