package com.jax.movies.data

import androidx.compose.ui.graphics.painter.Painter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("kinopoiskId") val kinopoiskId: Int,
    @SerialName("posterUrl") val image: String,
    @SerialName("nameRu") val nameRu: String,
    @SerialName("nameEn") val nameEn: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("shortDescription") val shortDescription: String? = null,
    @SerialName("genres") val genres: List<Genre>,
    @SerialName("ratingImdb") val rating: Double? = null,
    @SerialName("year") val year: Int? = null,
    @SerialName("filmLength") val filmLength: Int? = null,
    @SerialName("ratingAgeLimits") val ratingAgeLimits: String? = null,
    @SerialName("countries") val countries: List<Country>
)

