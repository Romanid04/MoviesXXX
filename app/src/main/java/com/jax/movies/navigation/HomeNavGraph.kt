package com.jax.movies.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jax.movies.model.MovieViewModel
import com.jax.movies.presentation.detail.MovieDetailScreen
import com.jax.movies.presentation.main.HomePage
import com.jax.movies.presentation.main.OneTypePage

sealed class HomeRoute(val route: String){
    object Home: HomeRoute("home")
    object MovieDetail: HomeRoute("movie_detail")
    object OneTypeMovies: HomeRoute("list_film/{category}"){
        fun createRoute(category: String): String {
            return "list_film/$category"
        }
    }
}

@Composable
fun HomeNavGraph(){
    val navController = rememberNavController()
    val homeViewModel: MovieViewModel = viewModel()

    NavHost(navController = navController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = HomeRoute.Home.route) {
        composable(
            route = HomeRoute.Home.route
        ) {
            HomePage(uiState = homeViewModel.uiState,
                navController = navController,
                onTypeClick = { category, movies ->
                    homeViewModel.setMovies(movies)
                    navController.navigate(HomeRoute.OneTypeMovies.createRoute(category))
                },
                onMovieClick = { movie ->
                    navController.navigate("${HomeRoute.MovieDetail.route}/${movie.kinopoiskId}")
                },
                retryAction = {
                    homeViewModel.getMovies()
                })

        }

        composable(route = HomeRoute.OneTypeMovies.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "Заглушка"
            val movies = homeViewModel.getMovie()

            OneTypePage(category = category,
                movie = movies,
                //navController = navController,
                onMovieClick = {
                    navController.navigate(HomeRoute.MovieDetail.route)
                },
                onBackClick = {
                    navController.navigate(HomeRoute.Home.route)
                }
            )
        }

        composable(
            route = "movieDetail/{kinopoiskId}",
            arguments = listOf(navArgument("kinopoiskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val kinopoiskId = backStackEntry.arguments?.getInt("kinopoiskId") ?: 0
            MovieDetailScreen(
                kinopoiskId = kinopoiskId,
                onBackClick = { navController.popBackStack() }
            )
        }




    }
}