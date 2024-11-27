package com.jax.movies.intent

sealed class MovieDetailIntent {
    data class FetchMovieDetails(val kinopoiskId: Int): MovieDetailIntent()
}