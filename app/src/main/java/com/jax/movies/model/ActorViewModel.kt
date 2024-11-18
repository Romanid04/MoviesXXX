package com.jax.movies.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.data.ActorDetailsResponse
import com.jax.movies.data.ActorFilm
import com.jax.movies.network.Api
import com.jax.movies.network.MovieApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class ActorFilmographyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<FilmographyUIState>(FilmographyUIState.Loading)
    val uiState: StateFlow<FilmographyUIState> get() = _uiState

    // Функция для загрузки фильмографии
    fun getFilmography(staffId: Int) {
        viewModelScope.launch {
            _uiState.value = FilmographyUIState.Loading
            try {
                val actorDetails = Api.retrofitService.getActorDetails(staffId)
                _uiState.value = FilmographyUIState.Success(actorDetails.films)
            } catch (e: Exception) {
                _uiState.value = FilmographyUIState.Error(e.localizedMessage ?: "Ошибка загрузки фильмографии")
            }
        }
    }
}

sealed class FilmographyUIState {
    object Loading : FilmographyUIState()
    data class Success(val films: List<ActorFilm>) : FilmographyUIState()
    data class Error(val message: String) : FilmographyUIState()
}
