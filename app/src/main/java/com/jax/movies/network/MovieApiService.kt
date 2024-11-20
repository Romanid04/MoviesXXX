package com.jax.movies.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jax.movies.data.ActorDetailsResponse
import com.jax.movies.data.ImageResponse
import com.jax.movies.data.Movie
import com.jax.movies.data.Response
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

private const val BASE_URL =
    "https://kinopoiskapiunofficial.tech/"

private const val API_KEY =
    "f21e5dec-bd33-4148-bcd4-ce71dd8c5595"

val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()


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

//    @GET("api/v2.2/films/{id}/images")
//    suspend fun getMovieImages(
//        @Path("id") kinopoiskId: Int,
//        @Header("X-API-KEY") apiKey: String = API_KEY,
//    ): ImageResponse

    @GET("api/v2.2/films/{id}/images")
    suspend fun getMovieImages(
        @Path("id") kinopoiskId: Int,
        @Query("type") type: String, // Фильтр по типу изображения
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

}


object Api {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}

