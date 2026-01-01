package com.sjani.usnationalparkguide.ui.screens.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sjani.usnationalparkguide.R
import com.sjani.usnationalparkguide.ui.theme.*
import com.sjani.usnationalparkguide.ui.viewmodel.CampDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampDetailScreen(viewModel: CampDetailViewModel, onBackClick: () -> Unit) {
    val campground by viewModel.campground.collectAsState()
    val context = LocalContext.current
    
    Scaffold(
        containerColor = Background
    ) { padding ->
        if (campground == null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment =
                    Alignment.Center
            ) {
                CircularProgressIndicator(color = Primary)
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with back button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(40.dp)
                                .background(Surface, CircleShape)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = TextPrimary
                            )
                        }
                        
                        Text(
                            text = "Campground",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        
                        IconButton(
                            onClick = { /* Search */ },
                            modifier = Modifier
                                .size(40.dp)
                                .background(Surface, CircleShape)
                        ) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = TextPrimary
                            )
                        }
                    }
                }
                
                // Campground image placeholder
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(200.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        // Use a camping icon as placeholder
                        Icon(
                            Icons.Default.Cabin,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Primary.copy(alpha = 0.5f)
                        )
                        
                        // Camp name overlay at bottom
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.5f))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = campground?.campName ?: "",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = Color.White
                            )
                            Text(
                                text = campground?.parkCode?.uppercase() ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
                
                Spacer(Modifier.height(20.dp))
                
                // Amenities section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CampAmenityIcon(
                        icon = Icons.Default.Wc,
                        label = "Toilets",
                        available = campground?.toilets?.isNotBlank() == true && campground?.toilets != "None"
                    )
                    CampAmenityIcon(
                        icon = Icons.Default.LocalFireDepartment,
                        label = "Fire Pit",
                        available = true // Assuming most camps have fire pits
                    )
                    CampAmenityIcon(
                        icon = Icons.Default.Shower,
                        label = "Showers",
                        available = campground?.showers?.isNotBlank() == true && campground?.showers != "None"
                    )
                    CampAmenityIcon(
                        icon = Icons.Default.DeleteOutline,
                        label = "Trash",
                        available = true // Assuming most camps have trash
                    )
                }
                
                Spacer(Modifier.height(24.dp))
                
                // Description section
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        text = "About this campground",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = campground?.description?.ifEmpty { "No description available" } ?: "No description available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Muted,
                        lineHeight = 24.sp
                    )
                }
                
                Spacer(Modifier.height(20.dp))
                
                // Accessibility card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Surface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        @Suppress("DEPRECATION")
                        Icon(
                            Icons.Default.Accessible,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Wheelchair Access",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = campground?.wheelchairAccess?.ifEmpty { "Information not available" } ?: "Information not available",
                                style = MaterialTheme.typography.bodySmall,
                                color = Muted
                            )
                        }
                    }
                }
                
                // Additional info cards
                Spacer(Modifier.height(12.dp))
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Surface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.SignalCellularAlt,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Cell Reception",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                            Text(
                                text = campground?.cellPhoneReception?.ifEmpty { "Unknown" } ?: "Unknown",
                                style = MaterialTheme.typography.bodySmall,
                                color = Muted
                            )
                        }
                    }
                }
                
                Spacer(Modifier.height(24.dp))
                
                // Action buttons
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Get Directions button
                    if (campground?.directionsUrl?.isNotBlank() == true) {
                        Button(
                            onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(campground?.directionsUrl))) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(26.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary)
                        ) {
                            Icon(Icons.Default.Navigation, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Get Directions",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    
                    // Reserve button
                    if (campground?.reservationsUrl?.isNotBlank() == true) {
                        Button(
                            onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(campground?.reservationsUrl))) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(26.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Primary)
                        ) {
                            Icon(Icons.Default.Event, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Reserve",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun CampAmenityIcon(
    icon: ImageVector,
    label: String,
    available: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (available) Surface else Surface.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = if (available) Primary else Muted.copy(alpha = 0.5f),
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (available) TextPrimary else Muted.copy(alpha = 0.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CampAmenityIconPreview() {
    USNationalParkGuideTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CampAmenityIcon(
                icon = Icons.Default.Shower,
                label = "Showers",
                available = true
            )
            CampAmenityIcon(
                icon = Icons.Default.Wifi,
                label = "WiFi",
                available = false
            )
        }
    }
}
