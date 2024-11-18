package com.jax.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Genre(
    @SerialName("genre") val name: String? = null
)