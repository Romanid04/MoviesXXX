package com.jax.movies.navigation

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.jax.movies.data.MovieViewModel
import com.jax.movies.presentation.detail.DetailContent
import com.jax.movies.presentation.main.BottomScreenItem
import com.jax.movies.presentation.main.HomePage
import com.jax.movies.presentation.main.ItemDetails
import com.jax.movies.presentation.movie.MovieContent
import com.jax.movies.presentation.profile.ProfileScreen
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
            HomePage(
                uiState = viewModel.uiState,
                onMovieClick = {
                    navController.navigate(ItemDetails.MovieDetail.route)
                },
                onTypeClick = {
                    category, movies ->
                    viewModel.setMovies(movies)
                    navController.navigate(HomeRoute.OneTypeMovies.createRoute(category))
                }
            )
        }
        composable(BottomScreenItem.SearchScreen.route) {
            SearchScreen(paddingValues,
                uiState = viewModel.uiState
        ) }
        composable(BottomScreenItem.ProfileScreen.route) { ProfileScreen(paddingValues) }
    }

}
