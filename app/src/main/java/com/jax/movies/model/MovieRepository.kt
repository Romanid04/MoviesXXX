package com.jax.movies.repository

import com.jax.movies.data.ActorDetailsResponse
import com.jax.movies.data.ImageResponse
import com.jax.movies.data.Movie
import com.jax.movies.data.Response
import com.jax.movies.data.SearchResponse
import com.jax.movies.data.SimilarMoviesResponse
import com.jax.movies.data.Staff
import com.jax.movies.network.MovieApiFactory


interface Repository{
    suspend fun getPremieres(): Response
    suspend fun getPopularMovies(): Response
    suspend fun getMilitants(): Response
    suspend fun getDramaOfFrance(): Response
    suspend fun getMovieDetails(kinopoisk: Int): Movie
    suspend fun getMovieImages(kinopoisk: Int): ImageResponse
    suspend fun getMovieStaff(staffId: Int): List<Staff>
    suspend fun getActorDetails(id: Int): ActorDetailsResponse
    suspend fun getSimilarMovies(filmId: Int): SimilarMoviesResponse
    suspend fun getMovieImages(kinopoisk: Int, type: String): ImageResponse
    suspend fun searchByKeyword(keyword: String): SearchResponse


}
class MovieRepository: Repository {
    private val retrofitService = MovieApiFactory.retrofitService
    override suspend fun getPremieres(): Response {
        return retrofitService.getPremieres()
    }
    override suspend fun getPopularMovies(): Response {
        return retrofitService.getPopularMovies()
    }
    override suspend fun getMilitants(): Response {
        return retrofitService.getMilitants()
    }
    override suspend fun getDramaOfFrance(): Response {
        return retrofitService.getDramaOfFrance()
    }

    override suspend fun getMovieDetails(kinopoisk: Int): Movie {
        return retrofitService.getMovieDetails(kinopoisk)
    }

    override suspend fun getMovieStaff(staffId: Int): List<Staff> {
        return retrofitService.getMovieStaff(staffId)
    }

    override suspend fun getActorDetails(kinopoisk: Int): ActorDetailsResponse {
        return retrofitService.getActorDetails(kinopoisk)
    }

    override suspend fun getMovieImages(kinopoisk: Int): ImageResponse {
        return retrofitService.getMovieImages(kinopoisk)
    }

    override suspend fun getSimilarMovies(filmId: Int): SimilarMoviesResponse {
        return retrofitService.getSimilarMovies(filmId)
    }

    override suspend fun getMovieImages(kinopoisk: Int, type: String): ImageResponse {
        return retrofitService.getMovieImages(kinopoisk, type)
    }

    override suspend fun searchByKeyword(keyword: String): SearchResponse {
        return retrofitService.searchByKeyword(keyword)
    }
}
