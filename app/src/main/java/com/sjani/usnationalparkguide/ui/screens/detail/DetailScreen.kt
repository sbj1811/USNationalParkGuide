package com.sjani.usnationalparkguide.ui.screens.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sjani.usnationalparkguide.data.entity.*
import com.sjani.usnationalparkguide.data.model.CurrentWeather
import com.sjani.usnationalparkguide.ui.theme.*
import com.sjani.usnationalparkguide.ui.viewmodel.DetailViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBackClick: () -> Unit,
    onTrailClick: (String) -> Unit,
    onCampgroundClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val park by viewModel.park.collectAsState()
    val trails by viewModel.trails.collectAsState()
    val campgrounds by viewModel.campgrounds.collectAsState()
    val alerts by viewModel.alerts.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    val isFavorite = remember(favorites, park) { 
        favorites.any { it.parkId == park?.parkId } 
    }
    
    val tabs = listOf("Overview", "Trails", "Campgrounds", "Alerts")
    val pagerState = rememberPagerState { tabs.size }
    
    Scaffold(
        containerColor = Background,
        floatingActionButton = {
            if (park != null) {
                FloatingActionButton(
                    onClick = {
                        park?.let { p ->
                            if (isFavorite) {
                                viewModel.removeFromFavorites(p.parkId)
                            } else {
                                viewModel.addToFavorites(p)
                            }
                        }
                    },
                    containerColor = if (isFavorite) Accent else Primary,
                    shape = CircleShape
                ) {
                    Icon(
                        if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = Color.White
                    )
                }
            }
        }
    ) { padding ->
        if (uiState.isLoading && park == null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Primary)
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Hero image section
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    AsyncImage(
                        model = park?.image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    
                    // Gradient overlay
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.4f),
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.6f)
                                    )
                                )
                            )
                    )
                    
                    // Top bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .statusBarsPadding(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                        
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, "Check out ${park?.parkName}!")
                                }
                                context.startActivity(Intent.createChooser(intent, "Share"))
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White.copy(alpha = 0.2f), CircleShape)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                        }
                    }
                    
                    // Park name and info at bottom
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(20.dp)
                    ) {
                        Text(
                            text = park?.parkName ?: "",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp
                            ),
                            color = Color.White
                        )
                        Text(
                            text = park?.states ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
                
                // Quick info chips
                if (park != null) {
                    LazyRow(
                        modifier = Modifier.padding(vertical = 12.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            InfoChip(
                                icon = Icons.Default.Terrain,
                                text = park?.designation?.ifBlank { "National Park" } ?: "National Park"
                            )
                        }
                        if (uiState.weather != null) {
                            item {
                                InfoChip(
                                    icon = Icons.Default.WbSunny,
                                    text = "${uiState.weather?.main?.temp?.roundToInt() ?: "--"}°F"
                                )
                            }
                        }
                        if (alerts.isNotEmpty()) {
                            item {
                                InfoChip(
                                    icon = Icons.Default.Warning,
                                    text = "${alerts.size} Alerts",
                                    isWarning = true
                                )
                            }
                        }
                    }
                }
                
                // Tab row
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Background,
                    contentColor = Primary,
                    edgePadding = 20.dp,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (pagerState.currentPage == index) FontWeight.Bold else FontWeight.Normal,
                                    color = if (pagerState.currentPage == index) Primary else Muted
                                )
                            }
                        )
                    }
                }
                
                HorizontalDivider(color = Divider)
                
                // Tab content
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> OverviewTab(park, uiState.weather, trails.take(3), onTrailClick)
                        1 -> TrailsTab(trails, onTrailClick)
                        2 -> CampgroundsTab(campgrounds, onCampgroundClick)
                        3 -> AlertsTab(alerts)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoChip(
    icon: ImageVector,
    text: String,
    isWarning: Boolean = false
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isWarning) WarningColor.copy(alpha = 0.15f) else Surface,
        border = BorderStroke(1.dp, if (isWarning) WarningColor.copy(alpha = 0.3f) else Divider)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = if (isWarning) WarningColor else Primary
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = if (isWarning) WarningColor else TextPrimary
            )
        }
    }
}

@Composable
fun OverviewTab(
    park: ParkEntity?,
    weather: CurrentWeather?,
    topTrails: List<TrailEntity>,
    onTrailClick: (String) -> Unit
) {
    val context = LocalContext.current
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // About section
        item {
            Text(
                text = "About this park",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = park?.description ?: "No description available",
                style = MaterialTheme.typography.bodyMedium,
                color = Muted,
                lineHeight = 24.sp
            )
        }
        
        // Weather card
        if (weather != null) {
            item {
                WeatherCard(weather)
            }
        }
        
        // Top Trails section
        if (topTrails.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Top Trails",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(topTrails, key = { it.trailId }) { trail ->
                        TrailMiniCard(
                            trail = trail,
                            onClick = { onTrailClick(trail.trailId) }
                        )
                    }
                }
            }
        }
        
        // Contact section
        item {
            Text(
                text = "Contact Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(Modifier.height(12.dp))
            
            ContactCard(
                icon = Icons.Default.LocationOn,
                label = "Address",
                value = park?.address ?: "Not available",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=${Uri.encode(park?.address)}"))
                    context.startActivity(intent)
                }
            )
            
            Spacer(Modifier.height(8.dp))
            
            ContactCard(
                icon = Icons.Default.Phone,
                label = "Phone",
                value = park?.phone ?: "Not available",
                onClick = {
                    if (park?.phone != "NA" && park?.phone != null) {
                        context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${park.phone}")))
                    }
                }
            )
            
            Spacer(Modifier.height(8.dp))
            
            ContactCard(
                icon = Icons.Default.Email,
                label = "Email",
                value = park?.email ?: "Not available",
                onClick = {
                    if (park?.email != "NA" && park?.email != null) {
                        context.startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${park.email}")))
                    }
                }
            )
        }
        
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun WeatherCard(weather: CurrentWeather) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Top) {
                        Text(
                            text = "${weather.main?.temp?.roundToInt() ?: "--"}",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 48.sp
                            ),
                            color = Primary
                        )
                        Text(
                            text = "°F",
                            style = MaterialTheme.typography.titleLarge,
                            color = Primary
                        )
                    }
                    Text(
                        text = weather.weather.firstOrNull()?.description?.replaceFirstChar { it.uppercase() } ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Muted
                    )
                }
                
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "H: ${weather.main?.tempMax?.roundToInt() ?: "--"}°",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Accent
                    )
                    Text(
                        text = "L: ${weather.main?.tempMin?.roundToInt() ?: "--"}°",
                        style = MaterialTheme.typography.bodyMedium,
                        color = InfoColor
                    )
                }
            }
            
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = Primary.copy(alpha = 0.2f))
            Spacer(Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherStat(label = "Humidity", value = "${weather.main?.humidity ?: "--"}%")
                WeatherStat(label = "Wind", value = "${weather.wind?.speed?.roundToInt() ?: "--"} mph")
                
                val fmt = SimpleDateFormat("h:mm a", Locale.getDefault())
                WeatherStat(
                    label = "Sunrise",
                    value = weather.sys?.sunrise?.let { fmt.format(Date(it * 1000)) } ?: "--"
                )
                WeatherStat(
                    label = "Sunset",
                    value = weather.sys?.sunset?.let { fmt.format(Date(it * 1000)) } ?: "--"
                )
            }
        }
    }
}

@Composable
fun WeatherStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Muted
        )
    }
}

@Composable
fun TrailMiniCard(
    trail: TrailEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = trail.imageSmall.ifEmpty { trail.imageMed },
                contentDescription = trail.trailName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface)
                    .padding(10.dp)
            ) {
                Text(
                    text = trail.trailName,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun ContactCard(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = Muted
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Muted
            )
        }
    }
}

@Composable
fun TrailsTab(trails: List<TrailEntity>, onTrailClick: (String) -> Unit) {
    if (trails.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No trails found", color = Muted)
        }
        return
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(trails, key = { it.trailId }) { trail ->
            TrailListCard(trail = trail, onClick = { onTrailClick(trail.trailId) })
        }
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun TrailListCard(trail: TrailEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = trail.imageSmall.ifEmpty { trail.imageMed },
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = trail.trailName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    DifficultyBadge(trail.difficulty)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "${trail.length} mi",
                        style = MaterialTheme.typography.bodySmall,
                        color = Muted
                    )
                    Text(
                        text = " · ${trail.ascent} ft",
                        style = MaterialTheme.typography.bodySmall,
                        color = Muted
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = trail.location,
                    style = MaterialTheme.typography.bodySmall,
                    color = Muted
                )
            }
        }
    }
}

@Composable
fun DifficultyBadge(difficulty: String) {
    val (color, text) = when (difficulty.lowercase()) {
        "green", "easy" -> SuccessColor to "Easy"
        "blue", "intermediate", "moderate" -> InfoColor to "Moderate"
        "black", "difficult", "hard" -> ErrorColor to "Hard"
        else -> Muted to difficulty
    }
    
    Surface(
        shape = RoundedCornerShape(4.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun CampgroundsTab(campgrounds: List<CampEntity>, onCampgroundClick: (String) -> Unit) {
    if (campgrounds.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No campgrounds found", color = Muted)
        }
        return
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(campgrounds, key = { it.campId }) { camp ->
            CampgroundListCard(camp = camp, onClick = { onCampgroundClick(camp.campId) })
        }
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun CampgroundListCard(camp: CampEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = camp.campName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = camp.description,
                style = MaterialTheme.typography.bodySmall,
                color = Muted,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(12.dp))
            
            // Amenities row
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (camp.toilets != "None") {
                    AmenityIcon(Icons.Default.Wc, "Restrooms")
                }
                if (camp.showers != "None") {
                    AmenityIcon(Icons.Default.Shower, "Showers")
                }
                if (camp.cellPhoneReception.isNotBlank() && camp.cellPhoneReception != "No") {
                    AmenityIcon(Icons.Default.SignalCellularAlt, "Cell Service")
                }
            }
        }
    }
}

@Composable
fun AmenityIcon(icon: ImageVector, description: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = description,
            tint = Primary,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = description,
            style = MaterialTheme.typography.labelSmall,
            color = Muted
        )
    }
}

@Composable
fun AlertsTab(alerts: List<AlertEntity>) {
    if (alerts.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = SuccessColor
                )
                Spacer(Modifier.height(12.dp))
                Text("No active alerts", color = Muted)
            }
        }
        return
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(alerts, key = { it.alertId }) { alert ->
            AlertCard(alert)
        }
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun AlertCard(alert: AlertEntity) {
    val (bgColor, iconColor) = when (alert.category.lowercase()) {
        "danger" -> ErrorColor.copy(alpha = 0.1f) to ErrorColor
        "caution" -> WarningColor.copy(alpha = 0.1f) to WarningColor
        else -> InfoColor.copy(alpha = 0.1f) to InfoColor
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = alert.alertName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
                Text(
                    text = alert.category,
                    style = MaterialTheme.typography.labelSmall,
                    color = iconColor.copy(alpha = 0.8f)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = alert.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextPrimary
                )
            }
        }
    }
}

// Preview helpers
private val sampleTrail = TrailEntity(
    trailId = "1",
    trailName = "Half Dome Trail",
    summary = "Iconic hike to the top of Half Dome",
    difficulty = "black",
    length = "14.2",
    ascent = "4800",
    location = "Yosemite Valley",
    condition = "Good"
)

private val sampleCamp = CampEntity(
    campId = "1",
    campName = "Upper Pines Campground",
    description = "Popular campground in Yosemite Valley",
    parkCode = "yose",
    showers = "Yes",
    toilets = "Flush Toilets",
    cellPhoneReception = "Limited",
    wheelchairAccess = "Accessible sites available"
)

private val sampleAlert = AlertEntity(
    alertId = "1",
    alertName = "Road Closure",
    description = "Tioga Road is closed for the winter season.",
    category = "Park Closure",
    parkCode = "yose"
)

@Preview(showBackground = true)
@Composable
private fun InfoChipPreview() {
    USNationalParkGuideTheme {
        InfoChip(icon = Icons.Default.Park, text = "National Park")
    }
}

@Preview(showBackground = true)
@Composable
private fun DifficultyBadgePreview() {
    USNationalParkGuideTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DifficultyBadge("green")
            DifficultyBadge("blue")
            DifficultyBadge("black")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TrailListCardPreview() {
    USNationalParkGuideTheme {
        TrailListCard(trail = sampleTrail, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun CampgroundListCardPreview() {
    USNationalParkGuideTheme {
        CampgroundListCard(camp = sampleCamp, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun AlertCardPreview() {
    USNationalParkGuideTheme {
        AlertCard(alert = sampleAlert)
    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherStatPreview() {
    USNationalParkGuideTheme {
        WeatherStat(label = "Humidity", value = "45%")
    }
}
