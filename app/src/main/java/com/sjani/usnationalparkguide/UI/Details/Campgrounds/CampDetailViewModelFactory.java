package com.sjani.usnationalparkguide.UI.Details.Campgrounds;


import com.sjani.usnationalparkguide.Utils.ParkRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CampDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ParkRepository repository;
    private final String apiKey;
    private final String parkCode;

    public CampDetailViewModelFactory(ParkRepository repository, String apiKey, String parkCode) {
        this.repository = repository;
        this.apiKey = apiKey;
        this.parkCode = parkCode;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CampDetailViewModel(repository, apiKey, parkCode);
    }
}
