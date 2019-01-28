package com.sjani.usnationalparkguide.UI.Details;

import com.sjani.usnationalparkguide.Utils.ParkRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static final String TAG = DetailViewModelFactory.class.getSimpleName();
    private final ParkRepository repository;
    private final String parkCode;
    private final String apiKey;
    private final String trailApiKey;
    private final String fields;
    private final String latLong;

    public DetailViewModelFactory(ParkRepository repository, String parkCode, String apiKey, String trailApiKey, String fields, String latLong) {
        this.repository = repository;
        this.parkCode = parkCode;
        this.apiKey = apiKey;
        this.trailApiKey = trailApiKey;
        this.fields = fields;
        this.latLong = latLong;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailViewModel(repository, parkCode, apiKey, trailApiKey, latLong, fields);
    }
}
