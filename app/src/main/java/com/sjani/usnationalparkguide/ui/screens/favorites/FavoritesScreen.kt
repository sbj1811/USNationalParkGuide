package com.sjani.usnationalparkguide.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sjani.usnationalparkguide.data.entity.FavParkEntity
import com.sjani.usnationalparkguide.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favorites: List<FavParkEntity>,
    onBackClick: () -> Unit,
    onParkClick: (FavParkEntity) -> Unit,
    onRemoveFavorite: (String) -> Unit
) {
    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Favorite Parks",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = Muted.copy(alpha = 0.5f)
                    )
                    Text(
                        text = "No Favorite Parks",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Parks you favorite will appear here",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Muted
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favorites, key = { it.parkId }) { park ->
                    FavoriteParkCard(
                        park = park,
                        onClick = { onParkClick(park) },
                        onRemove = { onRemoveFavorite(park.parkId) }
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteParkCard(
    park: FavParkEntity,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            AsyncImage(
                model = park.image.ifEmpty { null },
                contentDescription = park.parkName,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 50f
                        )
                    )
            )
            
            // Remove button
            IconButton(
                onClick = onRemove,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.5f)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Remove from favorites",
                        tint = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = park.parkName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = park.states,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
            
            // Favorite heart icon
            Icon(
                Icons.Default.Favorite,
                contentDescription = null,
                tint = Accent,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FavoritesScreenEmptyPreview() {
    USNationalParkGuideTheme {
        FavoritesScreen(
            favorites = emptyList(),
            onBackClick = {},
            onParkClick = {},
            onRemoveFavorite = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FavoritesScreenWithDataPreview() {
    USNationalParkGuideTheme {
        FavoritesScreen(
            favorites = listOf(
                FavParkEntity(
                    parkId = "1",
                    parkCode = "yose",
                    parkName = "Yosemite National Park",
                    states = "CA",
                    image = "",
                    latLong = ""
                ),
                FavParkEntity(
                    parkId = "2",
                    parkCode = "grca",
                    parkName = "Grand Canyon National Park",
                    states = "AZ",
                    image = "",
                    latLong = ""
                )
            ),
            onBackClick = {},
            onParkClick = {},
            onRemoveFavorite = {}
        )
    }
}

