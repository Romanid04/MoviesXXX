package com.jax.movies.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jax.movies.data.Response
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Header
import retrofit2.http.Query


val json = Json { ignoreUnknownKeys = true }

private const val BASE_URL =
    "https://kinopoiskapiunofficial.tech/api/v2.2/"

private const val API_KEY =
    "f70f35c2-7d9a-4a2a-9a2f-28269dea0f8c"

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

    @GET("films/premieres?year=2024&month=NOVEMBER")
    suspend fun getPremieres(
        @Header("X-API-KEY") apiKey: String = API_KEY
    ): Response

    @GET("films/premieres?year=2024&month=OCTOBER")
    suspend fun getPopularMovies(
        @Header("X-API-KEY") apiKey: String = API_KEY
    ): Response

    @GET("films/premieres?year=2024&month=MAY")
    suspend fun getMilitants(
        @Header("X-API-KEY") apiKey: String = API_KEY
    ): Response

    @GET("films/premieres?year=2024&month=SEPTEMBER")
    suspend fun getDramaOfFrance(
        @Header("X-API-KEY") apiKey: String = API_KEY
    ): Response
}

object Api {
    val retrofitService: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }
}

