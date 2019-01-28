package com.sjani.usnationalparkguide.UI.Details;


import com.sjani.usnationalparkguide.Data.AlertEntity;
import com.sjani.usnationalparkguide.Data.CampEntity;
import com.sjani.usnationalparkguide.Data.FavParkEntity;
import com.sjani.usnationalparkguide.Data.ParkEntity;
import com.sjani.usnationalparkguide.Data.TrailEntity;
import com.sjani.usnationalparkguide.Utils.ParkRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

public class DetailViewModel extends ViewModel {

    private static final String TAG = DetailViewModel.class.getSimpleName();
    private final LiveData<ParkEntity> park;
    private final ParkRepository repository;
    private LiveData<TrailEntity> trail;
    private LiveData<CampEntity> camp;
    private LiveData<FavParkEntity> favPark;
    private LiveData<List<FavParkEntity>> favParks;
    private LiveData<List<TrailEntity>> trails;
    private LiveData<List<CampEntity>> camps;
    private LiveData<List<AlertEntity>> alerts;
    private MediatorLiveData<List<FavParkEntity>> mediatorFavPark = new MediatorLiveData<>();
    private String parkCode;
    private String apiKey;
    private String trailApiKey;
    private String latLong;
    private String fields;

    public DetailViewModel(ParkRepository repository, String parkCode, String apiKey, String trailApiKey, String latLong, String fields) {
        this.repository = repository;
        this.parkCode = parkCode;
        this.apiKey = apiKey;
        this.trailApiKey = trailApiKey;
        this.latLong = latLong;
        this.fields = fields;
        park = this.repository.getParkFromDb(parkCode);
    }

    public LiveData<ParkEntity> getPark() {
        return park;
    }

    public void setFavPark(FavParkEntity park) {
        this.repository.updateFav(park);
    }

    public LiveData<List<FavParkEntity>> getfavpark() {
        favParks = this.repository.getFavPark();
        return favParks;
    }

    public void clearFav() {
        this.repository.clearFav();
    }


    public LiveData<List<TrailEntity>> getTrails() {
        trails = this.repository.getTrailListFromDb(trailApiKey, latLong);
        return trails;
    }

    public LiveData<List<CampEntity>> getCamps() {
        camps = this.repository.getCampListFromDb(parkCode, fields, apiKey);
        return camps;
    }

    public LiveData<List<AlertEntity>> getAlerts() {
        alerts = this.repository.getAlertListFromDb(parkCode, apiKey);
        return alerts;
    }
}
