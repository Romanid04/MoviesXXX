package com.jax.movies.presentation.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import com.jax.movies.model.MovieDetailViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.jax.movies.R
import com.jax.movies.data.Movie
import com.jax.movies.data.SimilarMovie
import com.jax.movies.data.SimilarMoviesResponse
import com.jax.movies.data.Staff
import com.jax.movies.model.MovieDetailUIState
import com.jax.movies.navigation.HomeRoute
import com.jax.movies.ui.theme.Blue1
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.jax.movies.intent.MovieDetailIntent
import com.jax.movies.presentation.profile.ProfileScreen

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}

@Composable
fun MovieDetailScreen(
    kinopoiskId: Int,
    viewModel: MovieDetailViewModel = viewModel(),
    onBackClick: () -> Unit,
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchMovieDetails(MovieDetailIntent.FetchMovieDetails(kinopoiskId))
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
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MovieDetailsContent(
                    movie = uiState.movie,
                    actors = uiState.actors,
                    onActorClick = { staffId ->
                        val route = HomeRoute.ActorPage.createRoute(staffId)
                        navController.navigate(route)
                    },
                    employees = uiState.employees,
                    galleryImages = uiState.galleryImages,
                    similarMovies = uiState.similarMovies,
                    onBackClick = onBackClick,
                    onGalleryClick = { movieId ->
                        navController.navigate(HomeRoute.GalleryPage.createRoute(movieId = movieId))
                    }
                )

            }

        }

        is MovieDetailUIState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Нет подключние к инtернету", color = MaterialTheme.colorScheme.error)
                    Button(onClick = { viewModel.fetchMovieDetails(MovieDetailIntent.FetchMovieDetails(kinopoiskId)) }) {
                        Text("Повторить")
                    }
                }
            }
        }
    }
}
@Composable
fun MovieDetailsContent(
    movie: Movie,
    onActorClick: (Int) -> Unit,
    actors: List<Staff>,
    employees: List<Staff>,
    galleryImages: List<String>,
    similarMovies: List<SimilarMovie>,
    onBackClick: () -> Unit,
    onGalleryClick: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(GetScreenWidth().dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.image),
                    contentDescription = "Movie Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                        .align(Alignment.BottomCenter)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${movie.rating ?: ""} ${movie.nameRu ?: ""}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "${movie.year}, ${movie.genres?.joinToString(", ") { it.name ?: "" } ?: "Жанр неизвестен"}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "${movie.countries.joinToString(", ") { it.name }} • ${movie.filmLength ?: "Неизвестно"} мин, ${
                                movie.ratingAgeLimits?.substring(3) ?: "?"
                            }+",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Лайк",
                                    tint = Color.White
                                )
                            }

                            IconButton(onClick = {  }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.bookmarkborder),
                                    contentDescription = "Сохранить",
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            IconButton(onClick = {  }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibleoff),
                                    contentDescription = "Скрыть",
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            IconButton(onClick = {  }) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Поделиться",
                                    tint = Color.White
                                )
                            }

                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "Ещё",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
            var isExpanded by remember { mutableStateOf(false) }

            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = movie.shortDescription ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Text(
                    text = movie.description ?: "",
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 5,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 18.dp)
                        .clickable { isExpanded = !isExpanded }
                )

                Spacer(Modifier.height(30.dp))

                SimpleRow("В фильме снимались", "${actors.size}", Modifier.padding(end = 4.dp))
                Spacer(Modifier.height(20.dp))
                StaffLazyRow(staffList = actors, onClick = onActorClick, numberColumnLazy = 4)

                Spacer(Modifier.height(20.dp))
                SimpleRow("Над фильмом работали", "${employees.size}", Modifier.padding(end = 4.dp))
                Spacer(Modifier.height(20.dp))
                StaffLazyRow(staffList = employees, onClick = onActorClick, numberColumnLazy = 2)

                Spacer(modifier = Modifier.height(8.dp))

                SimpleRow("Галерея", "${galleryImages.size}", Modifier.clickable { onGalleryClick(movie.kinopoiskId) })
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(galleryImages) { imageUrl ->
                        ImageCard(imageUrl)
                    }
                }

                SimpleRow("Похожие фильмы", "${similarMovies.size}",
                    Modifier.padding(end = 4.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(similarMovies) { movie ->
                        SimilarMovieCard(movie)
                    }
                }
            }
        }

        IconButton(
            onClick = { onBackClick() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Назад",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun SimpleRow(text: String, size: String, modifier: Modifier) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = size,
                color = Blue1,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.padding(end = 4.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.icontoleft),
                contentDescription = "Movie Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(9.dp)
            )
        }
    }

}


@Composable
fun SimilarMovieCard(movie: SimilarMovie) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrlPreview),
            contentDescription = movie.nameRu ?: movie.nameEn,
            modifier = Modifier
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = movie.nameRu ?: movie.nameEn ?: movie.nameOriginal ?: "Неизвестно",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ImageCard(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
            .clip(RoundedCornerShape(8.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun StaffCard(staff: Staff, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            //.background(Color.Red)
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable { onClick(staff.staffId) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = rememberAsyncImagePainter(staff.posterUrl),
            contentDescription = staff.nameRu ?: staff.nameEn ?: "Сотрудник",
            modifier = Modifier
                .fillMaxSize()
                .size(90.dp)
            //.clip(RoundedCornerShape(4.dp))
        )
        Column(
            modifier = Modifier.padding(4.dp),
        ) {
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
}

@Composable
fun StaffLazyRow(
    staffList: List<Staff>,
    onClick: (Int) -> Unit,
    numberColumnLazy: Int
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        items(staffList.chunked(numberColumnLazy)) { row ->
            Column(
                modifier = Modifier.padding(end = 6.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { staff ->
                    StaffCard(
                        staff = staff,
                        onClick = onClick
                    )
                }
            }
        }
    }
}


@Composable
fun GetScreenWidth(): Int {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    return screenWidthDp
}
