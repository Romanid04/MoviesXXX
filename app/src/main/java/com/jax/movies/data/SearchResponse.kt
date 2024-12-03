package com.jax.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SearchResponse (
    @SerialName("keyword") val keyword: String,
    @SerialName("pagesCount") val pagesCount: Int,
    @SerialName("searchFilmsCountResult") val searchFilmsCountResult: Int,
    @SerialName("films") val films: List<Film>
)

@Serializable
data class Film(
    @SerialName("filmId") val filmId: Int,
    @SerialName("nameRu") val nameRu: String?=null,
    @SerialName("nameEn") val nameEn: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("year") val year: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("filmLength") val filmLength: String? = null,
    @SerialName("countries") val countries: List<Country>,
    @SerialName("genres") val genres: List<Genre>,
    @SerialName("rating") val rating: String? = null,
    @SerialName("ratingVoteCount") val ratingVoteCount: Int? = null,
    @SerialName("posterUrl") val posterUrl: String? = null,
    @SerialName("posterUrlPreview") val posterUrlPreview: String? = null
)