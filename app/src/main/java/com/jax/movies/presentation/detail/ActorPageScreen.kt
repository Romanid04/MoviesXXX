package com.jax.movies.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items

import coil.compose.rememberAsyncImagePainter
import com.jax.movies.intent.ActorIntent
import com.jax.movies.model.ActorPageUIState
import com.jax.movies.model.ActorPageViewModel
@Composable
fun ActorPageScreen(
    staffId: Int,
    onBackClick: () -> Unit,
    onFilmographyClick: (Int) -> Unit,
    onFilmClick: (Int) -> Unit,
    viewModel: ActorPageViewModel = viewModel()
) {
    LaunchedEffect(staffId) {
        viewModel.fetchActorDetails(ActorIntent.GetActorDetails(staffId))
    }

    val uiState = viewModel.uiState.collectAsState().value

    when (uiState) {
        is ActorPageUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ActorPageUIState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Ошибка: ${uiState.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { viewModel.fetchActorDetails(ActorIntent.GetActorDetails(staffId)) }) {
                        Text("Повторить")
                    }
                }
            }
        }

        is ActorPageUIState.Success -> {
            val actorDetails = uiState.actorDetails

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                }

                Image(
                    painter = rememberAsyncImagePainter(actorDetails.posterUrl),
                    contentDescription = actorDetails.nameRu ?: actorDetails.nameEn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Text(
                    text = actorDetails.nameRu ?: actorDetails.nameEn ?: "Неизвестно",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Возраст: ${actorDetails.age ?: "Неизвестно"}")
                Text(text = "Дата рождения: ${actorDetails.birthday ?: "Неизвестно"}")
                Text(text = "Место рождения: ${actorDetails.birthplace ?: "Неизвестно"}")

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Фильмография",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    TextButton(onClick = { onFilmographyClick(staffId) }) {
                        Text("Показать всё")
                    }
                }

                if (actorDetails.films.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(actorDetails.films.take(6)) { film ->
                            FilmCardForActorPage(
                                film = film,
                                onClick = { onFilmClick(film.filmId) }
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Нет данных о фильмах.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
