package com.jax.movies.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Staff(
    @SerialName("staffId") val staffId: Int,
    @SerialName("nameRu") val nameRu: String? = null,
    @SerialName("nameEn") val nameEn: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("posterUrl") val posterUrl: String? = null,
    @SerialName("professionText") val professionText: String? = null,
    @SerialName("professionKey") val professionKey: String?  = null // Например: "DIRECTOR", "ACTOR" и т.д.
)
