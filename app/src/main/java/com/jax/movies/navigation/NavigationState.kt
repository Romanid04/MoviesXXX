package com.jax.movies.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            if (route == GRAPH.MAIN_GRAPH) {
                popUpTo(GRAPH.ON_BOARDING_SCREEN) {
                    inclusive = true // Удаляет экран онбординга из стека
                }
            } else {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }


//    fun navigateToMovie(id: Long,type:String) {
//        navHostController.navigate(Details.Movie.getRouteWithArgs(id,type)) {
//            popUpTo(navHostController.graph.findStartDestination().id) {
//                saveState = true
//            }
//            restoreState = true
//        }
//    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}

