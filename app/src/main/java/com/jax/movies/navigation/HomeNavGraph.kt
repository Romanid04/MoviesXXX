package com.jax.movies.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jax.movies.intent.MovieIntent
import com.jax.movies.model.MovieViewModel
import com.jax.movies.presentation.detail.ActorFilmographyScreen
import com.jax.movies.presentation.detail.ActorPageScreen
import com.jax.movies.presentation.detail.GalleryScreen
//import com.jax.movies.presentation.detail.GalleryScreen
import com.jax.movies.presentation.detail.MovieDetailScreen
import com.jax.movies.presentation.main.HomePage
import com.jax.movies.presentation.main.OneTypePage

sealed class HomeRoute(val route: String){
    object Home: HomeRoute("home")
    object MovieDetail : HomeRoute("movie_detail/{movieId}") {
        fun createRoute(movieId: Int): String = "movie_detail/$movieId"
    }
    object OneTypeMovies: HomeRoute("list_film/{category}"){
        fun createRoute(category: String): String {
            return "list_film/$category"
        }
    }
    object ActorPage : HomeRoute("actor_page/{staffId}") {
        fun createRoute(staffId: Int): String = "actor_page/$staffId"
    }
    object ActorFilmography : HomeRoute("actor_filmography/{staffId}") {
        fun createRoute(staffId: Int): String = "actor_filmography/$staffId"
    }
    object GalleryPage: HomeRoute("gallery/{movieId}"){
        fun createRoute(movieId: Int): String = "gallery/$movieId"
    }
}

@Composable
fun HomeNavGraph() {
    val navController = rememberNavController()
    val homeViewModel: MovieViewModel = viewModel()

    NavHost(
        navController = navController,
        route = GRAPH.HOME_GRAPH,
        startDestination = HomeRoute.Home.route
    ) {
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
                    homeViewModel.fetchMovies(MovieIntent.LoadImages)
                })

        }

        composable(
            route = HomeRoute.OneTypeMovies.route,
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

        composable(
            route = "movieDetail/{kinopoiskId}",
            arguments = listOf(navArgument("kinopoiskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val kinopoiskId = backStackEntry.arguments?.getInt("kinopoiskId") ?: 0
            MovieDetailScreen(
                kinopoiskId = kinopoiskId,
                onBackClick = { navController.popBackStack()},
                navController = navController
            )
        }

        composable(
            route = HomeRoute.ActorPage.route,
            arguments = listOf(navArgument("staffId") { type = NavType.IntType })
        ) { backStackEntry ->
            val staffId = backStackEntry.arguments?.getInt("staffId") ?: 0

            ActorPageScreen(
                staffId = staffId,
                onBackClick = { navController.popBackStack() },
                onFilmographyClick = { actorId ->
                    navController.navigate(HomeRoute.ActorFilmography.createRoute(actorId))
                },
                onFilmClick = { filmId ->
                    navController.navigate(HomeRoute.MovieDetail.createRoute(filmId))
                }
            )
        }




        composable(
            route = HomeRoute.ActorFilmography.route,
            arguments = listOf(navArgument("staffId") { type = NavType.IntType })
        ) { backStackEntry ->
            val staffId = backStackEntry.arguments?.getInt("staffId") ?: 0
            ActorFilmographyScreen(
                staffId = staffId,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = HomeRoute.GalleryPage.route,
            arguments = listOf(navArgument("movieId"){ type = NavType.IntType})
        ){ backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            GalleryScreen(movieId = movieId,
                onBackClick = { navController.popBackStack()})
        }
    }
}