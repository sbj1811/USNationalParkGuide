package com.sjani.usnationalparkguide.UI.Details.Trails;


import com.sjani.usnationalparkguide.Utils.ParkRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class TrailDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static final String TAG = TrailDetailViewModelFactory.class.getSimpleName();
    private final ParkRepository repository;
    private final String trailApiKey;
    private final String latLong;

    public TrailDetailViewModelFactory(ParkRepository repository, String trailApiKey, String latLong) {
        this.repository = repository;
        this.trailApiKey = trailApiKey;
        this.latLong = latLong;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TrailDetailViewModel(repository, trailApiKey, latLong);
    }
}
