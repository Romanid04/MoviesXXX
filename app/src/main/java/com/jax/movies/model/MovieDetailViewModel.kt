package com.jax.movies.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.jax.movies.data.Movie
import com.jax.movies.intent.MovieDetailIntent
import com.jax.movies.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException


class MovieDetailViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<MovieDetailUIState>(MovieDetailUIState.Initial)
    val uiState: StateFlow<MovieDetailUIState> get() = _uiState
    val repository = MovieRepository()

    fun fetchMovieDetails(intent: MovieDetailIntent){
        viewModelScope.launch {
            when (intent){
                is MovieDetailIntent.FetchMovieDetails -> getMovieDetails(intent.kinopoiskId)
            }
        }
    }


    private fun getMovieDetails(kinopoiskId: Int) {
        viewModelScope.launch {
            _uiState.value = MovieDetailUIState.Loading
            try {
                Log.d("MovieDetailViewModel", "Fetching details for movie ID: $kinopoiskId")

                val movie = repository.getMovieDetails(kinopoiskId)
                Log.d("MovieDetailViewModel", "Movie details fetched: $movie")

                val staff = repository.getMovieStaff(kinopoiskId)
                Log.d("MovieDetailViewModel", "Movie staff fetched: $staff")

                val actors = staff.filter { it.professionKey == "ACTOR" }
                val employees = staff.filter { it.professionKey != "ACTOR" }
                Log.d("MovieDetailViewModel", "Actors: $actors, Employees: $employees")

                val imagesResponse = repository.getMovieImages(kinopoiskId, "STILL")
                Log.d("MovieDetailViewModel", "Images fetched: ${imagesResponse.items}")

                val images = imagesResponse.items.map { it.imageUrl }

                val similarMoviesResponse = repository.getSimilarMovies(kinopoiskId)
                val similarMovies = similarMoviesResponse.items
                Log.d("MovieDetailViewModel", "Similar movies fetched: $similarMovies")

                _uiState.value = MovieDetailUIState.Success(
                    movie = movie,
                    actors = actors,
                    employees = employees,
                    galleryImages = images,
                    similarMovies = similarMovies
                )
            }
            catch (e: IOException) {
                Log.e("Network Error", e.message ?: "Unknown IO error")
                _uiState.value = MovieDetailUIState.Error("Network error. Please try again.")
            }
            catch (e: JsonSyntaxException) {
                Log.e("Parsing Error", "Error parsing JSON response", e)
                _uiState.value = MovieDetailUIState.Error("Error parsing data.")
            } catch (e: JsonParseException) {
                Log.e("Parsing Error", "Error parsing JSON", e)
                _uiState.value = MovieDetailUIState.Error("Invalid data format received.")
            }
            catch (e: HttpException) {
                val errorCode = e.code()  // HTTP error code
                val errorMessage = e.localizedMessage ?: "Unknown error"
                Log.e("Network Error", "HTTP error code: $errorCode, Message: $errorMessage")
                _uiState.value = MovieDetailUIState.Error(errorMessage)
            }
            catch (e: Exception) {
                Log.e("MovieDetailViewModel", "Error fetching movie details: ${e::class.java.name}", e)
                _uiState.value = MovieDetailUIState.Error(e.localizedMessage ?: "Error loading data")
            }
        }
    }
}