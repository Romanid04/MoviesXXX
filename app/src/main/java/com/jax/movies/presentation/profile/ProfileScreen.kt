package com.jax.movies.presentation.profile

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.lazy.items

import com.jax.movies.R
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.lazy.grid.*

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}
data class CollectionItemData(
    val name: String,
    val itemCount: Int,
    val iconRes: Int,
    val isUserCreated: Boolean = false
)
@Composable
fun ProfileScreen() {
    var collections by remember {
        mutableStateOf(
            listOf(
                CollectionItemData("Любимые", 0, R.drawable.like),
                CollectionItemData("Хочу посмотреть", 0, R.drawable.bookmark_black),
                CollectionItemData("Русское кино", 0, R.drawable.icon_profile, isUserCreated = true)
            )
        )
    }

    var showDialog by remember { mutableStateOf(false) }
    var newCollectionName by remember { mutableStateOf("") }

    val deleteCollection: (CollectionItemData) -> Unit = { collection ->
        collections = collections.filter { it != collection }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        item {
            SectionTitle(title = "Просмотрено")
        }

        item {
            ViewedMoviesSection()
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }


        item {
            SectionTitle(title = "Коллекции")
        }

        item {
            AddCollectionButton(onClick = { showDialog = true })
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth().height(400.dp),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(collections) { collection ->
                    CollectionCard(
                        collection = collection,
                        onDelete = deleteCollection
                    )
                }
            }
        }

        if (showDialog) {
            item {
                NewCollectionDialog(
                    onDismiss = { showDialog = false },
                    onAddCollection = { name ->
                        if (name.isNotBlank()) {
                            collections = collections + CollectionItemData(
                                name = name,
                                itemCount = 0,
                                iconRes = R.drawable.icon_profile,
                                isUserCreated = true
                            )
                        }
                        showDialog = false
                    },
                    newCollectionName = newCollectionName,
                    onNameChange = { newCollectionName = it }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            SectionTitle(title = "Вам было интересно")
        }
        item {
            InterestedMoviesSection()
        }
    }
}
@Composable
fun CollectionCard(
    collection: CollectionItemData,
    onDelete: (CollectionItemData) -> Unit
) {
    Box(
        modifier = Modifier
            .size(150.dp, 160.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            .background(Color.Transparent)
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = collection.iconRes),
                contentDescription = "Collection Icon",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = collection.name,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF3D3BFF))
                        .padding(3.dp)
                ) {
                    Text(
                        text = "${collection.itemCount}",
                        style = TextStyle(fontSize = 14.sp, color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        if (collection.isUserCreated) {
            IconButton(
                onClick = { onDelete(collection) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "Удалить коллекцию",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun ViewedMoviesSection() {
    val movies = listOf("Фильм 1", "Фильм 2", "Фильм 3", "Фильм 4")
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies) { movie ->
            MovieCard(title = movie)
        }
    }
}

@Composable
fun InterestedMoviesSection() {
    val movies = listOf("Фильм A", "Фильм B", "Фильм C", "Фильм D")
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies) { movie ->
            MovieCard(title = movie)
        }
    }
}

@Composable
fun MovieCard(title: String) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = TextStyle(fontSize = 16.sp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AddCollectionButton(onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Добавить коллекцию",
                modifier = Modifier.size(32.dp)
            )
        }
        Text(text = "Создать свою коллекцию")
    }
}

@Composable
fun NewCollectionDialog(
    onDismiss: () -> Unit,
    onAddCollection: (String) -> Unit,
    newCollectionName: String,
    onNameChange: (String) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Добавить новую коллекцию") },
        text = {
            TextField(
                value = newCollectionName,
                onValueChange = onNameChange,
                label = { Text("Название коллекции") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (newCollectionName.isNotBlank()) {
                        onAddCollection(newCollectionName)
                    }
                }
            ) {
                Text("Добавить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отменить")
            }
        }
    )
}
