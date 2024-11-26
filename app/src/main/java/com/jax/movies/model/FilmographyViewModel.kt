package com.jax.movies.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.data.ActorFilm
import com.jax.movies.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class ActorFilmographyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<FilmographyUIState>(FilmographyUIState.Loading)
    val uiState: StateFlow<FilmographyUIState> get() = _uiState

    val repository = MovieRepository()

    fun getFilmography(staffId: Int) {
        viewModelScope.launch {
            _uiState.value = FilmographyUIState.Loading
            try {
                val actorDetails = repository.getActorDetails(staffId)
                val filmsWithPosters = actorDetails.films.map { film ->
                    val posters = getMoviePosters(film.filmId)
                    film to posters.firstOrNull()
                }.map { (film, poster) ->
                    film.copy(description = poster)
                }
                _uiState.value = FilmographyUIState.Success(filmsWithPosters)
            } catch (e: Exception) {
                _uiState.value = FilmographyUIState.Error(e.localizedMessage ?: "Ошибка загрузки фильмографии")
            }
        }
    }
}
suspend fun getMoviePosters(kinopoiskId: Int): List<String> {
    return try {
        val repository = MovieRepository()
        val response = repository.getMovieImages(kinopoiskId, type = "POSTER")
        response.items.map { it.imageUrl }
    } catch (e: Exception) {
        emptyList()
    }
}
sealed class FilmographyUIState {
    object Loading : FilmographyUIState()
    data class Success(val films: List<ActorFilm>) : FilmographyUIState()
    data class Error(val message: String) : FilmographyUIState()
}
