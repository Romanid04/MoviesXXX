package com.jax.movies.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jax.movies.data.MovieViewModel
import com.jax.movies.presentation.detail.DetailContent
import com.jax.movies.presentation.main.HomePage
import com.jax.movies.presentation.main.OneTypePage
import kotlinx.coroutines.flow.callbackFlow

sealed class HomeRoute(val route: String){
    object Home: HomeRoute("home")
    object MovieDetail: HomeRoute("movie_detail")
    object OneTypeMovies: HomeRoute("list_film/{category}"){
        fun createRoute(category: String): String {
            return "list_film/category"
        }
    }
}
@Composable
fun HomeNavGraph(paddingValues: PaddingValues,
                 navController: NavHostController
){
    val homeViewModel: MovieViewModel = viewModel()

    NavHost(navController = navController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = HomeRoute.Home.route) {
        composable(
            route = HomeRoute.Home.route
        ) {
            HomePage(uiState = homeViewModel.uiState,
                onTypeClick = { category, movies ->
                    homeViewModel.setMovies(movies)
                    navController.navigate(HomeRoute.OneTypeMovies.createRoute(category))
                },
                onMovieClick = {
                    navController.navigate(HomeRoute.MovieDetail.route)
                })
        }

        composable(route = HomeRoute.OneTypeMovies.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "Заглушка"
            val movies = homeViewModel.getMovie()

            OneTypePage(category = category,
                movie = movies,
                onMovieClick = {
                    navController.navigate(HomeRoute.MovieDetail.route)
                },
                onBackClick = {
                    navController.navigate(HomeRoute.Home.route)
                }
            )
        }
        composable(HomeRoute.MovieDetail.route) {
            DetailContent(name = HomeRoute.MovieDetail.route)
        }
    }
}