package com.jax.movies.presentation.main

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jax.movies.R
import com.jax.movies.data.Movie
import com.jax.movies.data.UIState


@Composable
fun HomePage(
    uiState: UIState,
    onMovieClick: (Movie) -> Unit,
    onTypeClick: (String, List<Movie>) -> Unit,
    retryAction: () -> Unit
) {
    when (uiState) {
        is UIState.Initial -> {}
        is UIState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()

            }
        }
        is UIState.Success -> {

            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, bottom = 60.dp)
                    .verticalScroll(state = scrollState),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(Modifier.height(50.dp))
                Image(
                    painter = painterResource(R.drawable.vector),
                    contentDescription = null
                )
                val categories = listOf(
                    "Премьера", "Популярное",
                    "Боевики США", "Драма Франции", "Топ-250", "Сериалы"
                )
                val size = minOf(categories.size, uiState.movies.size)
                for (i in 0 until size) {
                    LazyRowItem(items = uiState.movies[i],
                        type = categories[i],
                        onMovieClick = onMovieClick,
                        onTypeClick = { category, movies ->
                            onTypeClick(category, movies)
                        })
                }

            }
        }

        is UIState.Error -> {
            Column (modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Нет подключние к инернету",
                    color = Color.Red
                )
                Button(onClick = { retryAction() }) {
                    Text(text = "Попробовать снова")
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: () -> Unit,
    width: Dp = 111.dp,
    height: Dp = 260.dp,
    imageHeight: Dp = 156.dp
) {
    Column(
        modifier = Modifier
            .width(width)
            .height(height)
            .clickable { onMovieClick() }
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .width(width)
                .height(imageHeight)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = movie.image),
                contentDescription = "постер фильма",
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.nameRu,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            modifier = Modifier.height(50.dp)
        )
        Text(
            text = movie.genres.firstOrNull()?.name.orEmpty(),
            fontSize = 10.sp
        )
    }
}

@Composable
fun LazyRowItem(
    items: List<Movie>,
    type: String,
    onMovieClick: (Movie) -> Unit,
    onTypeClick: (String, List<Movie>) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth().clickable { onTypeClick(type, items) },
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = type,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(items.take(6)) { item ->
                MovieItem(
                    movie = item,
                    onMovieClick = { onMovieClick(item) }
                )
            }
        }
    }
}
