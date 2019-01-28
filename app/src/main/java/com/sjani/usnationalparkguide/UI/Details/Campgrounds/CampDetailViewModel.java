package com.sjani.usnationalparkguide.UI.Details.Campgrounds;


import com.sjani.usnationalparkguide.Data.CampEntity;
import com.sjani.usnationalparkguide.Utils.ParkRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

class CampDetailViewModel extends ViewModel {

    private final ParkRepository repository;
    private final String apiKey;
    private final String parkCode;
    private LiveData<CampEntity> camp;

    public CampDetailViewModel(ParkRepository repository, String apiKey, String parkCode) {
        this.repository = repository;
        this.apiKey = apiKey;
        this.parkCode = parkCode;
    }

    public LiveData<CampEntity> getCamp(String campId) {
        camp = this.repository.getCampFromDb(campId);
        return camp;
    }

}

