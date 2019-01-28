package com.sjani.usnationalparkguide.UI.MainList;


import com.sjani.usnationalparkguide.Data.FavParkEntity;
import com.sjani.usnationalparkguide.Data.ParkEntity;
import com.sjani.usnationalparkguide.Utils.ParkRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainListViewModel extends ViewModel {

    private static final String TAG = MainListViewModel.class.getSimpleName();
    private final ParkRepository repository;
    private LiveData<List<ParkEntity>> parks;
    private LiveData<List<FavParkEntity>> favParks;

    public MainListViewModel(ParkRepository repository, String apiKey, String fields, String state, String maxResults) {
        this.repository = repository;
        parks = this.repository.getParkListFromDb(apiKey, fields, state, maxResults);
    }

    public LiveData<List<ParkEntity>> getParksfromViewModel() {
        return parks;
    }

    public LiveData<List<FavParkEntity>> getfavpark() {
        favParks = this.repository.getFavPark();
        return favParks;
    }

}
