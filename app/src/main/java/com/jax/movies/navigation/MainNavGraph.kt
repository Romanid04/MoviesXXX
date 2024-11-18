package com.jax.movies.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jax.movies.model.MovieViewModel

import com.jax.movies.presentation.main.BottomScreenItem
import com.jax.movies.presentation.search.SearchScreen

@Composable
fun MainNavGraph(
    paddingValues: PaddingValues,
    navController: NavHostController
) {

    val viewModel: MovieViewModel = viewModel()
    NavHost(
        navController = navController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = BottomScreenItem.HomeScreen.route
    ) {
        composable(BottomScreenItem.HomeScreen.route) {
            HomeNavGraph()
        }
        composable(BottomScreenItem.SearchScreen.route) {
            SearchScreen(paddingValues,
                uiState = viewModel.uiState
        ) }
        composable(BottomScreenItem.ProfileScreen.route) {}
    }

}
