package com.sjani.usnationalparkguide.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sjani.usnationalparkguide.data.entity.TrailEntity
import com.sjani.usnationalparkguide.data.repository.ParkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class TrailDetailViewModel(
    repository: ParkRepository,
    trailId: String
) : ViewModel() {
    
    val trail = repository.getTrail(trailId)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}

class TrailDetailViewModelFactory(
    private val repository: ParkRepository,
    private val trailId: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrailDetailViewModel::class.java)) {
            return TrailDetailViewModel(repository, trailId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


