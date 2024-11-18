package com.jax.movies.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import com.jax.movies.model.MovieDetailViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.jax.movies.data.Movie
import com.jax.movies.data.Staff
import com.jax.movies.model.MovieDetailUIState

@Composable
fun MovieDetailScreen(
    kinopoiskId: Int,
    viewModel: MovieDetailViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getMovieDetails(kinopoiskId)
    }

    when (uiState) {
        is MovieDetailUIState.Initial -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Инициализация...")
            }
        }

        is MovieDetailUIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MovieDetailUIState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MovieDetailsContent(
                    movie = uiState.movie,
                    actors = uiState.actors,
                    employees = uiState.employees,
                    galleryImages = uiState.galleryImages,
                    onBackClick = onBackClick
                )
            }

        }

        is MovieDetailUIState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Нет подключние к инернету", color = MaterialTheme.colorScheme.error)
                    Button(onClick = { viewModel.getMovieDetails(kinopoiskId) }) {
                        Text("Повторить")
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailsContent(movie: Movie, actors: List<Staff>, employees: List<Staff>, galleryImages: List<String>, onBackClick: () -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        // Back Button
        IconButton(onClick = onBackClick) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // Movie Poster
        Image(
            painter = rememberAsyncImagePainter(movie.image),
            contentDescription = "Poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        // Movie Details
        Text(
            text = movie.nameRu,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(text = "Год: ${movie.year}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Рейтинг: ${movie.rating}", style = MaterialTheme.typography.bodyMedium)
        Text(
            text = movie.shortDescription ?: "Описание отсутствует",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = movie.description ?: "Описание отсутствует",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Страны: ${movie.countries.joinToString(", ") { it.name }}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Жанры: ${movie.genres?.joinToString(", ") { it.name ?: "" } ?: "Нет жанров"}",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = (movie.ratingAgeLimits?.substring(3) ?: movie.ratingAgeLimits
            ?: "Лимит на возраст отсутствует") + "+",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(30.dp))

        Text(
            text = "В фильме снимались",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Всего актеров: ${actors.size}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Актеры
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(actors.chunked(4)) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { actor ->
                        StaffCard(actor)
                    }
                }
            }
        }

        Text(
            text = "Над фильмом работали",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Всего: ${employees.size}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Работники
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(employees.chunked(4)) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { employee ->
                        StaffCard(employee)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        Text(
            text = "Галерея",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Всего: ${galleryImages.size}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(galleryImages) { imageUrl ->
                ImageCard(imageUrl)
            }
        }

    }
}

@Composable
fun ImageCard(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
    )
}

@Composable
fun StaffCard(staff: Staff) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(staff.posterUrl),
            contentDescription = staff.nameRu ?: staff.nameEn ?: "Сотрудник",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            text = staff.nameRu ?: "Неизвестно",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Text(
            text = staff.description ?: "Роль неизвестна",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}
