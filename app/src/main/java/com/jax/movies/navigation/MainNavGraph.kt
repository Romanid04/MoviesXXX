package com.jax.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

import com.jax.movies.presentation.main.BottomScreenItem
import com.jax.movies.presentation.profile.ProfileScreen
import com.jax.movies.presentation.search.SearchScreen

@Composable
fun MainNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = BottomScreenItem.HomeScreen.route
    ) {
        composable(BottomScreenItem.HomeScreen.route) {
            HomeNavGraph()
        }
        composable(BottomScreenItem.SearchScreen.route) {
            SearchScreen(navController) }
        composable(BottomScreenItem.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}
