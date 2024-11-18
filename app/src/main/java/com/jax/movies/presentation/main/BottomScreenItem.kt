package com.jax.movies.presentation.main

import androidx.annotation.DrawableRes
import com.jax.movies.R

sealed class BottomScreenItem(
    val route: String,
    val title: String,
    @DrawableRes val iconId: Int
) {

    data object HomeScreen : BottomScreenItem(
        iconId = R.drawable.icon_home,
        route = HOME,
        title = HOME
    )

    data object SearchScreen : BottomScreenItem(
        iconId = R.drawable.icon_search,
        route = SEARCH,
        title = SEARCH
    )

    data object ProfileScreen : BottomScreenItem(
        iconId = R.drawable.icon_profile,
        route = PROFILE,
        title = PROFILE
    )
    private companion object {
        const val HOME = "home"
        const val SEARCH = "search"
        const val PROFILE = "profile"
    }
}
