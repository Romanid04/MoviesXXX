package com.jax.movies.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.jax.movies.model.GalleryUiState
import com.jax.movies.model.GalleryViewModel


@Composable
fun GalleryScreen(movieId: Int,
                  onBackClick: ()-> Unit){
    val galleryViewModel: GalleryViewModel = viewModel()
    val galleryUiState = galleryViewModel.uiState.collectAsState().value

    LaunchedEffect(movieId) {
        galleryViewModel.fetchGalleryImages(movieId)
    }

    when(galleryUiState){
        is GalleryUiState.Initial -> {}
        is GalleryUiState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }

        is GalleryUiState.Success -> {
            val galleryItems = galleryUiState.galleryItems
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back to movie details page"
                        )
                    }
                    Spacer(Modifier.weight(0.8f))
                    Text(
                        text = "Галерея",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(Modifier.weight(1f))
                }
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(300.dp),
                    contentPadding = PaddingValues(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(galleryItems.chunked(2)) { index, galleryItem ->
                        if (index % 2 == 0) {
                            SingleItem(galleryItem[0])
                        } else AlternatingItem(galleryItem[0], galleryItem[1])
                    }
                }
            }
        }
        is GalleryUiState.Error -> {
            Text("Could not fetch images")
        }
    }
}

@Composable
fun AlternatingItem(galleryItem1: String, galleryItem2: String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(galleryItem1),
            contentDescription = "gallery item",
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.5f),
            contentScale = ContentScale.Crop
            )
        Image(painter = rememberAsyncImagePainter(galleryItem2),
            contentDescription = "second gallery item",
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.5f),
        contentScale = ContentScale.Crop)
    }
}

@Composable
fun SingleItem(galleryItem: String){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)) {
        Image(painter = rememberAsyncImagePainter(galleryItem),
            contentDescription = "single gallery item",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop)
    }
}

