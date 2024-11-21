package com.jax.movies.data

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponse(
    val items: List<ImageItem>
)

@Serializable
data class ImageItem(
    val imageUrl: String
)