package com.jax.movies.presentation.search

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.jax.movies.R
import com.jax.movies.data.Film
import com.jax.movies.model.SearchUiState
import com.jax.movies.model.SearchViewModel
import com.jax.movies.model.UIState
import com.jax.movies.navigation.HomeRoute

@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchScreen(navController: NavHostController) {
        val viewModel: SearchViewModel = viewModel()
        val uiState = viewModel.uiState.collectAsState().value

        val searchText by viewModel.searchText.collectAsState()
        val isSearching by viewModel.isSearching.collectAsState()


        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SearchBar(
                    query = searchText,
                    onQueryChange = viewModel::onSearchTextChange,
                    onSearch = { viewModel.findFilmsByKeyword(it) },
                    active = isSearching,
                    onActiveChange = { viewModel.onToogleSearch() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(70.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    shape = RoundedCornerShape(30.dp),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "search icon",
                            tint = Color.Gray
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painterResource(R.drawable.filter_icon),
                            contentDescription = "filter icon",
                            tint = Color.Gray,
                            modifier = Modifier.size(23.dp)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Фильмы, актеры, режиссеры",
                            color = Color.Gray,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Start
                        )
                    }
                ) { }
            },
            content = { innerPadding ->
                when (uiState) {
                    is SearchUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is SearchUiState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            val result = uiState.searchResults
                            items(result) { film ->
                                FilmItem(film, navController)
                            }
                        }
                    }

                    is SearchUiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Error fetching data",
                                color = Color.Red,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    is SearchUiState.Initial -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Начните поиск фильмов с самым лучшим качеством",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.width(250.dp)
                            )
                        }
                    }
                }
            }
        )
    }


@Composable
fun FilmItem(film: Film, navController: NavController){
    Row(modifier = Modifier.padding(start = 10.dp)
//        .clickable {
//            navController.navigate("movieDetail/${film.filmId}")
//        }
        ) {
        Log.d("FilmItem", "Poster URL: ${film.posterUrl}")
        Image(
            painter = rememberAsyncImagePainter(model = film.posterUrl),
            contentDescription = "постер фильма",
            modifier = Modifier.width(120.dp)
                .height(180.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Spacer(Modifier.height(15.dp))
            Text(
                text = film.nameRu?: (film.nameEn?: "Unknown"),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${ if(film.year==null || film.year=="null") "Год неизвестен" else film.year}, ${
                    if (film.genres.isNotEmpty()) film.genres[0].name else "Жанр неизвестен"
                }"
            )
        }
    }
}