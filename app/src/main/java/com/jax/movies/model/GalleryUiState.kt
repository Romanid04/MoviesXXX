package com.jax.movies.model

import com.jax.movies.navigation.HomeRoute

sealed class GalleryUiState {
    object Initial: GalleryUiState()
    object Loading: GalleryUiState()
    data class Success(val galleryItems: List<String>): GalleryUiState()
    data class Error(val message: String): GalleryUiState()
}