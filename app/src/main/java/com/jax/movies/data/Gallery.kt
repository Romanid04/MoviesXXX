package com.jax.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Gallery(
    @SerialName("posterUrl") val image: String,

)
