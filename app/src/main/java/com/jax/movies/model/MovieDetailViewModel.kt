package com.jax.movies.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.data.Staff
import com.jax.movies.network.Api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MovieDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<MovieDetailUIState>(MovieDetailUIState.Initial)
    val uiState: StateFlow<MovieDetailUIState> get() = _uiState


    fun getMovieDetails(kinopoiskId: Int) {
        viewModelScope.launch {
            _uiState.value = MovieDetailUIState.Loading
            try {
                val movie = Api.retrofitService.getMovieDetails(kinopoiskId = kinopoiskId)
                val staff = Api.retrofitService.getMovieStaff(filmId = kinopoiskId)
                val actors = staff.filter { it.professionKey == "ACTOR" }
                val employees = staff.filter { it.professionKey != "ACTOR" }

                val imagesResponse = Api.retrofitService.getMovieImages(kinopoiskId, "STILL")
                val images = imagesResponse.items.map { it.imageUrl }

                val similarMoviesResponse = Api.retrofitService.getSimilarMovies(kinopoiskId)
                val similarMovies = similarMoviesResponse.items

                _uiState.value = MovieDetailUIState.Success(
                    movie = movie,
                    actors = actors,
                    employees = employees,
                    galleryImages = images,
                    similarMovies = similarMovies
                )

            } catch (e: Exception) {
                _uiState.value =
                    MovieDetailUIState.Error(e.localizedMessage ?: "Ошибка загрузки данных")
                Log.e("API Exception", e.message.toString())
            }
        }
    }


}
