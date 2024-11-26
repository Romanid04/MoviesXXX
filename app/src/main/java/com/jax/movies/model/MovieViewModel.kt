package com.jax.movies.model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jax.movies.data.Movie
import com.jax.movies.repository.MovieRepository
import kotlinx.coroutines.launch


class MovieViewModel: ViewModel() {

    val repository = MovieRepository()

    var uiState by mutableStateOf<UIState>(UIState.Initial)
        private set

    private var movie: List<Movie> = emptyList()

    init{
        getMovies()
    }

    fun setMovies(movie: List<Movie>){
        this.movie = movie;
    }
    fun getMovie(): List<Movie> {
        return movie
    }

    fun getMovies() {
        viewModelScope.launch {
            uiState = UIState.Loading
            uiState = try {
                val premieres = repository.getPremieres()
                val popular = repository.getPopularMovies()
                val militants = repository.getMilitants()
                val dramaOfFrance = repository.getDramaOfFrance()

                UIState.Success(
                    movies = listOf(
                        premieres.items,
                        popular.items,
                        militants.items,
                        dramaOfFrance.items
                    )
                )
            } catch (e: Exception) {
                Log.e("API Exception", e.message.toString())
                UIState.Error
            }
        }
    }
}