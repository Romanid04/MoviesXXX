package com.jax.movies.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.intent.GalleryIntent
import com.jax.movies.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GalleryViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<GalleryUiState>(GalleryUiState.Initial)
    val uiState: StateFlow<GalleryUiState> = _uiState
    val repository = MovieRepository()

    fun fetchGallery(intent: GalleryIntent){
        viewModelScope.launch {
            when(intent){
                is GalleryIntent.LoadImages -> fetchGalleryImages(intent.movieId)
            }
        }
    }

    private fun fetchGalleryImages(movieId: Int) {
        viewModelScope.launch {
            _uiState.value = GalleryUiState.Loading
            try {
                val images = repository.getMovieImages(movieId, "STILL")
                val galleryImages = images.items.map { it.imageUrl }
                _uiState.value = GalleryUiState.Success(galleryImages)
            } catch (e: Exception) {
                _uiState.value = GalleryUiState.Error("Could not load images")
            }
        }
    }
}