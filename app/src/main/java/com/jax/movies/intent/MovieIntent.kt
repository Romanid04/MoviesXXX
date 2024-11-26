package com.jax.movies.intent


sealed class MovieIntent {
    object LoadImages: MovieIntent()
}