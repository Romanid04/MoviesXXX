package com.jax.movies.intent

sealed class GalleryIntent {
    data class LoadImages(val movieId: Int): GalleryIntent()
}