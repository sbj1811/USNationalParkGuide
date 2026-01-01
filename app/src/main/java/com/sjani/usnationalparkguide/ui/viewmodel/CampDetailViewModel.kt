package com.sjani.usnationalparkguide.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sjani.usnationalparkguide.data.entity.CampEntity
import com.sjani.usnationalparkguide.data.repository.ParkRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class CampDetailViewModel(
    repository: ParkRepository,
    campId: String
) : ViewModel() {
    
    val campground = repository.getCampground(campId)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
}

class CampDetailViewModelFactory(
    private val repository: ParkRepository,
    private val campId: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CampDetailViewModel::class.java)) {
            return CampDetailViewModel(repository, campId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


