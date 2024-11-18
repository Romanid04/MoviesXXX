package com.jax.movies.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.jax.movies.data.ActorFilm
@Composable
fun FilmCard(film: ActorFilm) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = film.nameRu ?: film.nameEn ?: "Без названия", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Рейтинг: ${film.ye ?: "Нет данных"}", style = MaterialTheme.typography.bodySmall)
            Text(text = film.description ?: "Нет описания", style = MaterialTheme.typography.bodySmall)
        }
    }
}