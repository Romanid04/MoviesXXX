package com.jax.movies.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jax.movies.presentation.main.MainPages
import com.jax.movies.presentation.main.OnBoardingScreen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        route = GRAPH.ROOT,
        startDestination = GRAPH.MAIN_GRAPH,
    ) {
        composable(route = GRAPH.ON_BOARDING_SCREEN) {
            OnBoardingScreen(onFinish = {
                navController.navigate(GRAPH.MAIN_GRAPH){
                    popUpTo(GRAPH.ON_BOARDING_SCREEN){
                        inclusive = true
                    }
                }
            })

        }
        composable(route = GRAPH.MAIN_GRAPH) {
            MainPages()
        }
    }
}

object GRAPH {
    const val ROOT = "root"
    const val ON_BOARDING_SCREEN = "onBoarding"
    const val MAIN_GRAPH = "main"
}
