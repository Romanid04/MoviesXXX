package com.jax.movies.presentation.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jax.movies.intent.FilmographyIntent
import com.jax.movies.model.ActorFilmographyViewModel
import com.jax.movies.model.FilmographyUIState
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorFilmographyScreen(
    staffId: Int,
    onBackClick: () -> Unit,
    viewModel: ActorFilmographyViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val selectedProfession = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(staffId) {
        viewModel.fetchFilmography(FilmographyIntent.GetFilmography(staffId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Фильмография") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        content = { padding ->
            when (uiState) {
                is FilmographyUIState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is FilmographyUIState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = (uiState as FilmographyUIState.Error).message,
                                color = MaterialTheme.colorScheme.error
                            )
                            Button(onClick = { viewModel.fetchFilmography(FilmographyIntent.GetFilmography(staffId)) }) {
                                Text("Повторить")
                            }
                        }
                    }
                }

                is FilmographyUIState.Success -> {
                    val films = (uiState as FilmographyUIState.Success).films
                    val professions = films.mapNotNull { it.professionKey }.distinct()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(professions) { profession ->
                                //val isSelected = selectedProfession.value == profession
                                Button(
                                    onClick = {
                                        selectedProfession.value = if (selectedProfession.value == profession) null else profession
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (selectedProfession.value == profession) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.surface
                                        },
                                        contentColor = if (selectedProfession.value == profession) {
                                            MaterialTheme.colorScheme.onPrimary
                                        } else {
                                            MaterialTheme.colorScheme.onSurface
                                        }
                                    ),
                                    border = if (selectedProfession.value == profession) {
                                        BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                                    } else {
                                        BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                ) {
                                    Text(
                                        text = profession.capitalize(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                            }
                        }


                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val filteredFilms = if (selectedProfession.value != null) {
                                films.filter { it.professionKey == selectedProfession.value }
                            } else {
                                films
                            }

                            items(filteredFilms) { film ->
                                FilmCard(film = film)
                            }
                        }
                    }
                }
            }
        }
    )
}
