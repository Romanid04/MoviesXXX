package com.jax.movies.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jax.movies.data.ActorDetailsResponse
import com.jax.movies.data.ImageResponse
import com.jax.movies.data.Movie
import com.jax.movies.data.Response
import com.jax.movies.data.SearchResponse
import com.jax.movies.data.SimilarMoviesResponse
import com.jax.movies.data.Staff
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


val json = Json { ignoreUnknownKeys = true }

private const val API_KEY =
    "f70f35c2-7d9a-4a2a-9a2f-28269dea0f8c"


interface MovieApiService {

    @GET("api/v2.2/films/premieres?year=2024&month=NOVEMBER")
    suspend fun getPremieres(
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): Response

    @GET("api/v2.2/films/premieres?year=2024&month=OCTOBER")
    suspend fun getPopularMovies(
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): Response

    @GET("api/v2.2/films/premieres?year=2024&month=MAY")
    suspend fun getMilitants(
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): Response

    @GET("api/v2.2/films/premieres?year=2024&month=SEPTEMBER")
    suspend fun getDramaOfFrance(
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): Response

    @GET("api/v2.2/films/{id}")
    suspend fun getMovieDetails(
        @Path("id") kinopoiskId: Int,
        @Header("X-API-KEY") apiKey:     String = API_KEY,
    ): Movie

    @GET("api/v1/staff?")
    suspend fun getMovieStaff(
        @Query("filmId") filmId: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): List<Staff>

//    @GET("api/v2.2/films/{filmId}/images")
//    suspend fun getMovieImages(
//        @Path("filmId") kinopoiskId: Int,
//        @Header("X-API-KEY") apiKey: String = API_KEY,
//    ): ImageResponse

    @GET("api/v2.2/films/{id}/images")
    suspend fun getMovieImages(
        @Path("id") kinopoiskId: Int,
        @Query("type") type: String = "STILL",
        @Header("X-API-KEY") apiKey: String = API_KEY
    ): ImageResponse

    @GET("/api/v1/staff/{id}")
    suspend fun getActorDetails(
        @Path("id") id: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY,
    ): ActorDetailsResponse

    @GET("api/v2.2/films/{id}/similars")
    suspend fun getSimilarMovies(
        @Path("id") filmId: Int,
        @Header("X-API-KEY") apiKey: String = API_KEY
    ): SimilarMoviesResponse

    @GET("api/v2.1/films/search-by-keyword")
    suspend fun searchByKeyword(
        @Query("keyword") keyword: String,
        @Header("X-API-KEY") apiKey: String = API_KEY
    ): SearchResponse

}

