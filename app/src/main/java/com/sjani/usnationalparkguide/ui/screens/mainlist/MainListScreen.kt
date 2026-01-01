package com.sjani.usnationalparkguide.ui.screens.mainlist

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import com.sjani.usnationalparkguide.R
import com.sjani.usnationalparkguide.data.entity.ParkEntity
import com.sjani.usnationalparkguide.ui.theme.*
import com.sjani.usnationalparkguide.ui.viewmodel.MainListViewModel
import kotlinx.coroutines.launch

// State code to full name mapping
private val stateNames = mapOf(
    "AL" to "Alabama", "AK" to "Alaska", "AZ" to "Arizona", "AR" to "Arkansas",
    "CA" to "California", "CO" to "Colorado", "CT" to "Connecticut", "DE" to "Delaware",
    "FL" to "Florida", "GA" to "Georgia", "HI" to "Hawaii", "ID" to "Idaho",
    "IL" to "Illinois", "IN" to "Indiana", "IA" to "Iowa", "KS" to "Kansas",
    "KY" to "Kentucky", "LA" to "Louisiana", "ME" to "Maine", "MD" to "Maryland",
    "MA" to "Massachusetts", "MI" to "Michigan", "MN" to "Minnesota", "MS" to "Mississippi",
    "MO" to "Missouri", "MT" to "Montana", "NE" to "Nebraska", "NV" to "Nevada",
    "NH" to "New Hampshire", "NJ" to "New Jersey", "NM" to "New Mexico", "NY" to "New York",
    "NC" to "North Carolina", "ND" to "North Dakota", "OH" to "Ohio", "OK" to "Oklahoma",
    "OR" to "Oregon", "PA" to "Pennsylvania", "RI" to "Rhode Island", "SC" to "South Carolina",
    "SD" to "South Dakota", "TN" to "Tennessee", "TX" to "Texas", "UT" to "Utah",
    "VT" to "Vermont", "VA" to "Virginia", "WA" to "Washington", "WV" to "West Virginia",
    "WI" to "Wisconsin", "WY" to "Wyoming", "DC" to "District of Columbia",
    "AS" to "American Samoa", "GU" to "Guam", "MP" to "Northern Mariana Islands",
    "PR" to "Puerto Rico", "VI" to "Virgin Islands"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainListScreen(
    viewModel: MainListViewModel,
    state: String,
    onParkClick: (ParkEntity) -> Unit,
    onSettingsClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onSignOut: () -> Unit,
    currentUser: FirebaseUser?
) {
    val uiState by viewModel.uiState.collectAsState()
    val parks by viewModel.parks.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    
    val filteredParks = remember(parks, searchQuery) {
        if (searchQuery.isBlank()) parks
        else parks.filter { 
            it.parkName.contains(searchQuery, ignoreCase = true) ||
            it.states.contains(searchQuery, ignoreCase = true)
        }
    }
    
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(24.dp)
                ) {
                    
                    // User avatar
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (currentUser?.photoUrl != null) {
                            AsyncImage(
                                model = currentUser.photoUrl,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = Primary
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    Text(
                        text = currentUser?.displayName ?: "Guest",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = currentUser?.email ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Muted
                    )
                    
                    Spacer(Modifier.height(24.dp))
                    HorizontalDivider(color = Divider)
                    Spacer(Modifier.height(16.dp))
                    
                    // Drawer items
                    DrawerItem(
                        icon = Icons.Default.Favorite,
                        label = "Favorite Parks",
                        badgeCount = favorites.size,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onFavoritesClick()
                        }
                    )
                    
                    DrawerItem(
                        icon = Icons.Default.Settings,
                        label = "Settings",
                        onClick = {
                            scope.launch { drawerState.close() }
                            onSettingsClick()
                        }
                    )
                    
                    DrawerItem(
                        icon = Icons.Default.Policy,
                        label = "Privacy Policy",
                        onClick = {
                            scope.launch { drawerState.close() }
                            context.startActivity(
                                Intent(Intent.ACTION_VIEW, Uri.parse("https://sbj1811.wixsite.com/npgapp"))
                            )
                        }
                    )
                    
                    Spacer(Modifier.weight(1f))
                    
                    HorizontalDivider(color = Divider)
                    Spacer(Modifier.height(8.dp))
                    
                    DrawerItem(
                        icon = Icons.AutoMirrored.Filled.ExitToApp,
                        label = "Sign Out",
                        onClick = {
                            scope.launch { drawerState.close() }
                            onSignOut()
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            containerColor = Background
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
            ) {
                // Custom header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // App icon and title in pill shape
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        color = Surface,
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_app_square),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "National Park Guide",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }
                    
                    Spacer(Modifier.width(12.dp))
                    
                    // Profile button
                    Surface(
                        modifier = Modifier.size(44.dp),
                        shape = CircleShape,
                        color = Surface,
                        shadowElevation = 2.dp,
                        onClick = { scope.launch { drawerState.open() } }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Primary
                            )
                        }
                    }
                }
                
                // Search bar
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(28.dp),
                    color = Surface,
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = Muted
                        )
                        Spacer(Modifier.width(12.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Search parks...",
                                    color = Muted,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            androidx.compose.foundation.text.BasicTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                textStyle = MaterialTheme.typography.bodyLarge.copy(
                                    color = TextPrimary
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                        Icon(
                            Icons.Default.Mic,
                            contentDescription = null,
                            tint = Muted
                        )
                    }
                }
                
                Spacer(Modifier.height(24.dp))
                
                // Loading or content
                when {
                    uiState.isLoading && parks.isEmpty() -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Primary)
                        }
                    }
                    parks.isEmpty() -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No parks found", color = Muted)
                        }
                    }
                    else -> {
                        // Pick a random featured park (stable across recompositions)
                        val featuredPark = remember(filteredParks) {
                            filteredParks.randomOrNull()
                        }
                        
                        // Get remaining parks (all except featured)
                        val remainingParks = remember(filteredParks, featuredPark) {
                            filteredParks.filter { it.parkId != featuredPark?.parkId }
                        }
                        
                        // Get state full name
                        val stateFullName = stateNames[state] ?: state
                        
                        // Featured section
                        if (featuredPark != null) {
                            SectionHeader(
                                title = "Featured",
                                onSeeAllClick = null
                            )
                            
                            Spacer(Modifier.height(12.dp))
                            
                            // Featured large card
                            FeaturedParkCard(
                                park = featuredPark,
                                onClick = { onParkClick(featuredPark) }
                            )
                            
                            Spacer(Modifier.height(24.dp))
                        }
                        
                        // All Parks in <State Name> section as grid
                        if (remainingParks.isNotEmpty()) {
                            SectionHeader(
                                title = "All Parks in $stateFullName",
                                onSeeAllClick = null
                            )
                            
                            Spacer(Modifier.height(12.dp))
                            
                            // Grid of parks
                            Column(
                                modifier = Modifier.padding(horizontal = 20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                remainingParks.chunked(2).forEach { rowParks ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        rowParks.forEach { park ->
                                            GridParkCard(
                                                park = park,
                                                onClick = { onParkClick(park) },
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                        // Fill empty space if odd number
                                        if (rowParks.size == 1) {
                                            Spacer(Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        }
                        
                        Spacer(Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        if (badgeCount > 0) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Accent
            ) {
                Text(
                    text = badgeCount.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        if (onSeeAllClick != null) {
            TextButton(onClick = onSeeAllClick) {
                Text(
                    text = "See All",
                    color = Muted
                )
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Muted
                )
            }
        }
    }
}

@Composable
fun FeaturedParkCard(
    park: ParkEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(220.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
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
                            startY = 100f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
            ) {
                Text(
                    text = park.parkName.replace("National Park", "").trim(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = park.designation.ifBlank { "National Park" },
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
fun GridParkCard(
    park: ParkEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .aspectRatio(0.9f)
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
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = park.parkName.replace("National Park", "").trim(),
                    style = MaterialTheme.typography.titleSmall,
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
        }
    }
}

// Preview helpers
private val samplePark = ParkEntity(
    parkId = "1",
    parkCode = "yose",
    parkName = "Yosemite National Park",
    description = "Yosemite National Park is in California's Sierra Nevada mountains.",
    states = "CA",
    designation = "National Park",
    latLong = "lat:37.8651, long:-119.5383",
    address = "Yosemite Valley, CA",
    phone = "(209) 372-0200",
    email = "yose_info@nps.gov",
    image = ""
)

@Preview(showBackground = true)
@Composable
private fun FeaturedParkCardPreview() {
    USNationalParkGuideTheme {
        FeaturedParkCard(
            park = samplePark,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GridParkCardPreview() {
    USNationalParkGuideTheme {
        GridParkCard(
            park = samplePark,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderPreview() {
    USNationalParkGuideTheme {
        SectionHeader(
            title = "Featured",
            onSeeAllClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DrawerItemPreview() {
    USNationalParkGuideTheme {
        DrawerItem(
            icon = Icons.Default.Favorite,
            label = "Favorite Parks",
            badgeCount = 5,
            onClick = {}
        )
    }
}
