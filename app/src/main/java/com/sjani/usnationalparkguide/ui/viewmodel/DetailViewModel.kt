package com.sjani.usnationalparkguide.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sjani.usnationalparkguide.data.entity.AlertEntity
import com.sjani.usnationalparkguide.data.entity.CampEntity
import com.sjani.usnationalparkguide.data.entity.FavParkEntity
import com.sjani.usnationalparkguide.data.entity.ParkEntity
import com.sjani.usnationalparkguide.data.entity.TrailEntity
import com.sjani.usnationalparkguide.data.model.CurrentWeather
import com.sjani.usnationalparkguide.data.repository.ParkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DetailUiState(
    val isLoading: Boolean = true,
    val park: ParkEntity? = null,
    val isFavorite: Boolean = false,
    val weather: CurrentWeather? = null,
    val trails: List<TrailEntity> = emptyList(),
    val campgrounds: List<CampEntity> = emptyList(),
    val alerts: List<AlertEntity> = emptyList(),
    val error: String? = null
)

class DetailViewModel(
    private val repository: ParkRepository,
    private val parkCode: String,
    private val latLong: String,
    private val npsApiKey: String,
    private val trailApiKey: String,
    private val weatherApiKey: String,
    private val campgroundFields: String
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()
    
    val park = repository.getPark(parkCode)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
    
    val trails = repository.getAllTrails()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val campgrounds = repository.getAllCampgrounds()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val alerts = repository.getAllAlerts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val favorites = repository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    init {
        loadData()
    }
    
    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Load trails
                repository.refreshTrails(trailApiKey, latLong)
                
                // Load campgrounds
                repository.refreshCampgrounds(parkCode, campgroundFields, npsApiKey)
                
                // Load alerts
                repository.refreshAlerts(parkCode, npsApiKey)
                
                // Load weather
                val weather = repository.getCurrentWeather(latLong, weatherApiKey)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    weather = weather,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
    
    fun addToFavorites(park: ParkEntity) {
        viewModelScope.launch {
            val favParkEntity = FavParkEntity.fromParkEntity(park)
            repository.addFavorite(favParkEntity)
        }
    }
    
    fun removeFromFavorites(parkId: String) {
        viewModelScope.launch {
            repository.removeFavorite(parkId)
        }
    }
    
    fun refreshWeather() {
        viewModelScope.launch {
            val weather = repository.getCurrentWeather(latLong, weatherApiKey)
            _uiState.value = _uiState.value.copy(weather = weather)
        }
    }
}

class DetailViewModelFactory(
    private val repository: ParkRepository,
    private val parkCode: String,
    private val latLong: String,
    private val npsApiKey: String,
    private val trailApiKey: String,
    private val weatherApiKey: String,
    private val campgroundFields: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                repository,
                parkCode,
                latLong,
                npsApiKey,
                trailApiKey,
                weatherApiKey,
                campgroundFields
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

