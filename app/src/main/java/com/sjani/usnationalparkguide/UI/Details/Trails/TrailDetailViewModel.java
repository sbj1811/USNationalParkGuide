package com.sjani.usnationalparkguide.UI.Details.Trails;


import com.sjani.usnationalparkguide.Data.TrailEntity;
import com.sjani.usnationalparkguide.Utils.ParkRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class TrailDetailViewModel extends ViewModel {

    private final ParkRepository repository;
    private String trailApiKey;
    private String latLong;
    private LiveData<TrailEntity> trail;

    public TrailDetailViewModel(ParkRepository repository, String trailApiKey, String latLong) {
        this.repository = repository;
        this.trailApiKey = trailApiKey;
        this.latLong = latLong;

    }

    public LiveData<TrailEntity> getTrail(String trailId) {
        trail = this.repository.getTrailFromDb(trailId);
        return trail;
    }


}
