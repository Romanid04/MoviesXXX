package com.jax.movies.intent

sealed class ActorIntent {
    data class GetActorDetails(val staffId: Int): ActorIntent()
}