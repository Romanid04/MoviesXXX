package com.jax.movies.intent

sealed class FilmographyIntent {
    data class GetFilmography(val staffId: Int): FilmographyIntent()
}