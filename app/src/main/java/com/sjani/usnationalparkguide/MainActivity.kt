package com.sjani.usnationalparkguide

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sjani.usnationalparkguide.data.database.AppDatabase
import com.sjani.usnationalparkguide.data.network.NetworkModule
import com.sjani.usnationalparkguide.data.repository.ParkRepository
import com.sjani.usnationalparkguide.ui.navigation.Screen
import com.sjani.usnationalparkguide.ui.screens.detail.CampDetailScreen
import com.sjani.usnationalparkguide.ui.screens.detail.DetailScreen
import com.sjani.usnationalparkguide.ui.screens.detail.TrailDetailScreen
import com.sjani.usnationalparkguide.ui.screens.favorites.FavoritesScreen
import com.sjani.usnationalparkguide.ui.screens.mainlist.MainListScreen
import com.sjani.usnationalparkguide.ui.screens.onboarding.OnboardingScreen
import com.sjani.usnationalparkguide.ui.screens.settings.SettingsScreen
import com.sjani.usnationalparkguide.ui.theme.Background
import com.sjani.usnationalparkguide.ui.theme.USNationalParkGuideTheme
import com.sjani.usnationalparkguide.ui.viewmodel.CampDetailViewModel
import com.sjani.usnationalparkguide.ui.viewmodel.CampDetailViewModelFactory
import com.sjani.usnationalparkguide.ui.viewmodel.DetailViewModel
import com.sjani.usnationalparkguide.ui.viewmodel.DetailViewModelFactory
import com.sjani.usnationalparkguide.ui.viewmodel.MainListViewModel
import com.sjani.usnationalparkguide.ui.viewmodel.MainListViewModelFactory
import com.sjani.usnationalparkguide.ui.viewmodel.TrailDetailViewModel
import com.sjani.usnationalparkguide.ui.viewmodel.TrailDetailViewModelFactory

class MainActivity : ComponentActivity() {
    
    private lateinit var firebaseAuth: FirebaseAuth
    private var currentUser by mutableStateOf<FirebaseUser?>(null)
    
    // Settings state
    private var selectedState by mutableStateOf("CA")
    private var maxResults by mutableStateOf("50")
    
    // Onboarding state
    private var hasSeenOnboarding by mutableStateOf(false)
    
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            currentUser = firebaseAuth.currentUser
            Toast.makeText(this, "Signed in successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show()
        }
    }
    
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
        // Handle permission results
    }
    
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _ ->
        // Handle notification permission result
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen before super.onCreate()
        installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display for Android 15+ compatibility
        enableEdgeToEdge()
        
        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser
        
        // Load saved preferences
        val prefs = getSharedPreferences("park_prefs", MODE_PRIVATE)
        selectedState = prefs.getString("selected_state", "CA") ?: "CA"
        maxResults = prefs.getString("max_results", "50") ?: "50"
        hasSeenOnboarding = prefs.getBoolean("has_seen_onboarding", false)
        
        // Request location permissions
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
        
        // Request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        
        setContent {
            USNationalParkGuideTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Background
                ) {
                    NPGApp(
                        currentUser = currentUser,
                        selectedState = selectedState,
                        maxResults = maxResults,
                        hasSeenOnboarding = hasSeenOnboarding,
                        onOnboardingComplete = {
                            hasSeenOnboarding = true
                            getSharedPreferences("park_prefs", MODE_PRIVATE)
                                .edit()
                                .putBoolean("has_seen_onboarding", true)
                                .apply()
                            launchSignIn()
                        },
                        onStateChanged = { state ->
                            selectedState = state
                            savePreferences()
                        },
                        onMaxResultsChanged = { results ->
                            maxResults = results
                            savePreferences()
                        },
                        onSignOut = {
                            AuthUI.getInstance().signOut(this).addOnCompleteListener {
                                currentUser = null
                                launchSignIn()
                            }
                        }
                    )
                }
            }
        }
    }
    
    override fun onResume() {
        super.onResume()
        currentUser = firebaseAuth.currentUser
    }
    
    private fun launchSignIn() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .setTheme(R.style.Theme_USNationalParkGuide_FirebaseAuth)
            .setLogo(R.drawable.ic_app_square)
            .build()
        
        signInLauncher.launch(signInIntent)
    }
    
    private fun savePreferences() {
        getSharedPreferences("park_prefs", MODE_PRIVATE)
            .edit()
            .putString("selected_state", selectedState)
            .putString("max_results", maxResults)
            .apply()
    }
}

@Composable
fun NPGApp(
    currentUser: FirebaseUser?,
    selectedState: String,
    maxResults: String,
    hasSeenOnboarding: Boolean,
    onOnboardingComplete: () -> Unit,
    onStateChanged: (String) -> Unit,
    onMaxResultsChanged: (String) -> Unit,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    
    // Create repository
    val database = remember { AppDatabase.getInstance(context) }
    val repository = remember {
        ParkRepository(
            parkDao = database.parkDao(),
            favDao = database.favDao(),
            trailDao = database.trailDao(),
            campDao = database.campDao(),
            alertDao = database.alertDao(),
            npsApiService = NetworkModule.npsApiService,
            hikingProjectApiService = NetworkModule.hikingProjectApiService,
            weatherApiService = NetworkModule.weatherApiService
        )
    }
    
    // API Keys from resources
    val npsApiKey = context.getString(R.string.NPSapiKey)
    val trailApiKey = context.getString(R.string.HPapiKey)
    val weatherApiKey = context.getString(R.string.OWMapiKey)
    val fields = context.getString(R.string.fields)
    val campgroundFields = context.getString(R.string.fields_cg)
    
    // Determine start destination
    val startDestination = if (!hasSeenOnboarding) {
        Screen.Onboarding.route
    } else {
        Screen.MainList.route
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Onboarding screen
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onGetStarted = {
                    onOnboardingComplete()
                    navController.navigate(Screen.MainList.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.MainList.route) {
            val viewModel: MainListViewModel = viewModel(
                factory = MainListViewModelFactory(
                    repository = repository,
                    apiKey = npsApiKey,
                    fields = fields,
                    state = selectedState,
                    maxResults = maxResults
                )
            )
            
            // Trigger refresh when state or maxResults changes
            LaunchedEffect(selectedState, maxResults) {
                viewModel.updateStateAndRefresh(selectedState, maxResults)
            }
            
            MainListScreen(
                viewModel = viewModel,
                state = selectedState,
                currentUser = currentUser,
                onParkClick = { park ->
                    navController.navigate(Screen.ParkDetail.createRoute(park.parkCode, park.latLong))
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onFavoritesClick = {
                    navController.navigate(Screen.Favorites.route)
                },
                onSignOut = onSignOut
            )
        }
        
        composable(Screen.Favorites.route) {
            val favorites by repository.getAllFavorites().collectAsState(initial = emptyList())
            val scope = rememberCoroutineScope()
            
            FavoritesScreen(
                favorites = favorites,
                onBackClick = { navController.popBackStack() },
                onParkClick = { favPark ->
                    navController.navigate(Screen.ParkDetail.createRoute(favPark.parkCode, favPark.latLong))
                },
                onRemoveFavorite = { parkId ->
                    scope.launch {
                        repository.removeFavorite(parkId)
                    }
                }
            )
        }
        
        composable(
            route = Screen.ParkDetail.route,
            arguments = listOf(
                navArgument("parkCode") { type = NavType.StringType },
                navArgument("latLong") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val parkCode = backStackEntry.arguments?.getString("parkCode") ?: ""
            val encodedLatLong = backStackEntry.arguments?.getString("latLong") ?: ""
            val latLong = Screen.ParkDetail.decodeLatLong(encodedLatLong)
            
            val viewModel: DetailViewModel = viewModel(
                factory = DetailViewModelFactory(
                    repository = repository,
                    parkCode = parkCode,
                    latLong = latLong,
                    npsApiKey = npsApiKey,
                    trailApiKey = trailApiKey,
                    weatherApiKey = weatherApiKey,
                    campgroundFields = campgroundFields
                )
            )
            
            DetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onTrailClick = { trailId ->
                    navController.navigate(Screen.TrailDetail.createRoute(trailId))
                },
                onCampgroundClick = { campId ->
                    navController.navigate(Screen.CampDetail.createRoute(campId))
                }
            )
        }
        
        composable(
            route = Screen.TrailDetail.route,
            arguments = listOf(
                navArgument("trailId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val trailId = backStackEntry.arguments?.getString("trailId") ?: ""
            
            val viewModel: TrailDetailViewModel = viewModel(
                factory = TrailDetailViewModelFactory(
                    repository = repository,
                    trailId = trailId
                )
            )
            
            TrailDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.CampDetail.route,
            arguments = listOf(
                navArgument("campId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val campId = backStackEntry.arguments?.getString("campId") ?: ""
            
            val viewModel: CampDetailViewModel = viewModel(
                factory = CampDetailViewModelFactory(
                    repository = repository,
                    campId = campId
                )
            )
            
            CampDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                currentState = selectedState,
                currentMaxResults = maxResults,
                onStateChanged = { state ->
                    onStateChanged(state)
                    navController.popBackStack()
                },
                onMaxResultsChanged = { results ->
                    onMaxResultsChanged(results)
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
