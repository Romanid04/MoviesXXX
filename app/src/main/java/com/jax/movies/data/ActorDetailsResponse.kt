package com.jax.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDetailsResponse(
    @SerialName("personId") val personId: Int,
    @SerialName("nameRu") val nameRu: String?,
    @SerialName("nameEn") val nameEn: String?,
    @SerialName("posterUrl") val posterUrl: String?,
    @SerialName("sex") val sex: String?,
    @SerialName("birthday") val birthday: String?,
    @SerialName("death") val death: String?,
    @SerialName("age") val age: Int?,
    @SerialName("birthplace") val birthplace: String?,
    @SerialName("profession") val profession: String?,
    @SerialName("facts") val facts: List<String>?,
    @SerialName("films") val films: List<ActorFilm>
)

@Serializable
data class ActorFilm(
    @SerialName("filmId") val filmId: Int,
    @SerialName("nameRu") val nameRu: String?,
    @SerialName("nameEn") val nameEn: String?,
    @SerialName("rating") val rating: String?,
    @SerialName("description") val description: String?,
    @SerialName("professionKey") val professionKey: String?
)