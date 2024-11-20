package com.jax.movies.data

import kotlinx.serialization.Serializable


@Serializable
data class SimilarMoviesResponse(
    val total: Int,
    val items: List<SimilarMovie>
)

@Serializable
data class SimilarMovie(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val relationType: String
)
