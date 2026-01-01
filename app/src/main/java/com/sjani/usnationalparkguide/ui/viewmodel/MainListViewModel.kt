package com.sjani.usnationalparkguide.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sjani.usnationalparkguide.data.entity.FavParkEntity
import com.sjani.usnationalparkguide.data.entity.ParkEntity
import com.sjani.usnationalparkguide.data.repository.ParkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MainListUiState(
    val isLoading: Boolean = true,
    val parks: List<ParkEntity> = emptyList(),
    val favoritePark: FavParkEntity? = null,
    val error: String? = null
)

class MainListViewModel(
    private val repository: ParkRepository,
    private val apiKey: String,
    private val fields: String,
    initialState: String,
    initialMaxResults: String
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(MainListUiState())
    val uiState: StateFlow<MainListUiState> = _uiState.asStateFlow()
    
    private var currentState = initialState
    private var currentMaxResults = initialMaxResults
    
    val parks = repository.getAllParks()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    val favorites = repository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    
    init {
        loadParks()
    }
    
    fun loadParks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                repository.refreshParks(apiKey, fields, currentState, currentMaxResults)
                _uiState.value = _uiState.value.copy(isLoading = false, error = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
    
    fun updateStateAndRefresh(newState: String, newMaxResults: String) {
        if (newState != currentState || newMaxResults != currentMaxResults) {
            currentState = newState
            currentMaxResults = newMaxResults
            // Clear existing parks before loading new ones
            viewModelScope.launch {
                repository.clearParks()
                loadParks()
            }
        }
    }
    
    fun refresh() {
        loadParks()
    }
}

class MainListViewModelFactory(
    private val repository: ParkRepository,
    private val apiKey: String,
    private val fields: String,
    private val state: String,
    private val maxResults: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainListViewModel::class.java)) {
            return MainListViewModel(repository, apiKey, fields, state, maxResults) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

