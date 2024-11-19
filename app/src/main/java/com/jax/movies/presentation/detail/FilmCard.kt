package com.jax.movies.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jax.movies.data.ActorFilm
@Composable
fun FilmCard(film: ActorFilm) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = film.nameRu ?: film.nameEn ?: "Без названия", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Рейтинг: ${film.rating ?: "Нет данных"}", style = MaterialTheme.typography.bodySmall)
            Text(text = film.description ?: "Нет описания", style = MaterialTheme.typography.bodySmall)
        }
    }
}