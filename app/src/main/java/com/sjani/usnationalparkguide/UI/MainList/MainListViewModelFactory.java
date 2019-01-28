package com.sjani.usnationalparkguide.UI.MainList;


import com.sjani.usnationalparkguide.Utils.ParkRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class MainListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static final String TAG = MainListViewModelFactory.class.getSimpleName();
    private ParkRepository parkRepository;
    private String apiKey;
    private String fields;
    private String state;
    private String maxResult;

    public MainListViewModelFactory(ParkRepository parkRepository, String apiKey, String fields, String state, String maxResult) {
        this.parkRepository = parkRepository;
        this.apiKey = apiKey;
        this.fields = fields;
        this.state = state;
        this.maxResult = maxResult;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainListViewModel(parkRepository, apiKey, fields, state, maxResult);
    }
}
