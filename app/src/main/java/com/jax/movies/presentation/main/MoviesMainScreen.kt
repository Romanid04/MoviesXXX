package com.jax.movies.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.jax.movies.navigation.HomeNavGraph
import com.jax.movies.navigation.MainNavGraph
import com.jax.movies.navigation.RootNavGraph


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviesMainScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        RootNavGraph()
    }
}


@Composable
fun MainPages() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onHomeClick = {
                    navController.navigate(BottomScreenItem.HomeScreen.route)
                },
                onProfileClick = {
                    navController.navigate(BottomScreenItem.ProfileScreen.route)
                },
                onSearchClick = {
                    navController.navigate(BottomScreenItem.SearchScreen.route)
                }
            )
        }
    ) { paddingValues ->
        MainNavGraph(
            paddingValues = paddingValues,
            navController = navController
        )
    }
}