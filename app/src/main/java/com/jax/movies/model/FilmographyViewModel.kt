package com.jax.movies.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.data.ActorFilm
import com.jax.movies.network.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class ActorFilmographyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<FilmographyUIState>(FilmographyUIState.Loading)
    val uiState: StateFlow<FilmographyUIState> get() = _uiState

    fun getFilmography(staffId: Int) {
        viewModelScope.launch {
            _uiState.value = FilmographyUIState.Loading
            try {
                val actorDetails = Api.retrofitService.getActorDetails(staffId)
                val filmsWithPosters = actorDetails.films.map { film ->
                    val posters = getMoviePosters(film.filmId) // Используем только POSTER
                    film to posters.firstOrNull() // Берем первый доступный постер
                }.map { (film, poster) ->
                    film.copy(description = poster) // Сохраняем URL постера в description
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
        val response = Api.retrofitService.getMovieImages(kinopoiskId, type = "POSTER")
        response.items.map { it.imageUrl } // Извлекаем URL постеров
    } catch (e: Exception) {
        emptyList() // Возвращаем пустой список в случае ошибки
    }
}
sealed class FilmographyUIState {
    object Loading : FilmographyUIState()
    data class Success(val films: List<ActorFilm>) : FilmographyUIState()
    data class Error(val message: String) : FilmographyUIState()
}
