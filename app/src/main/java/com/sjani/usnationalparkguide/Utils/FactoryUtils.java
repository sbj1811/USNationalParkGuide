package com.sjani.usnationalparkguide.Utils;

import android.content.Context;

import com.sjani.usnationalparkguide.Data.AlertDatabase;
import com.sjani.usnationalparkguide.Data.CampDatabase;
import com.sjani.usnationalparkguide.Data.FavDatabase;
import com.sjani.usnationalparkguide.Data.ParkDatabase;
import com.sjani.usnationalparkguide.Data.TrailDatabase;
import com.sjani.usnationalparkguide.UI.Details.Campgrounds.CampDetailViewModelFactory;
import com.sjani.usnationalparkguide.UI.Details.DetailViewModelFactory;
import com.sjani.usnationalparkguide.UI.Details.Trails.TrailDetailViewModelFactory;
import com.sjani.usnationalparkguide.UI.MainList.MainListViewModelFactory;

import androidx.annotation.NonNull;

public class FactoryUtils {
    private static final String TAG = FactoryUtils.class.getSimpleName();


    public static MainListViewModelFactory provideMLVFactory(Context context, String apiKey, String fields, String state, String maxResults) {
        ParkRepository repository = getParkRepository(context);
        return new MainListViewModelFactory(repository, apiKey, fields, state, maxResults);
    }

    @NonNull
    private static ParkRepository getParkRepository(Context context) {
        ParkDatabase database = ParkDatabase.getInstance(context.getApplicationContext());
        FavDatabase favDatabase = FavDatabase.getInstance(context.getApplicationContext());
        TrailDatabase trailDatabase = TrailDatabase.getInstance(context.getApplicationContext());
        CampDatabase campDatabase = CampDatabase.getInstance(context.getApplicationContext());
        AlertDatabase alertDatabase = AlertDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return new ParkRepository(database.parkDoa(), favDatabase.favDoa(), trailDatabase.trailDao(), campDatabase.campDao(), alertDatabase.alertDao(), executors);
    }

    public static DetailViewModelFactory provideDVMFactory(Context context, String parkCode, String trailId, String campId, String apiKey, String trailApiKey, String fields, String latLong) {
        ParkRepository repository = getParkRepository(context);
        return new DetailViewModelFactory(repository, parkCode, apiKey, trailApiKey, fields, latLong);
    }

    public static TrailDetailViewModelFactory provideTDVMFactory(Context context, String trailApiKey, String latLong) {
        ParkRepository repository = getParkRepository(context);
        return new TrailDetailViewModelFactory(repository, trailApiKey, latLong);
    }

    public static CampDetailViewModelFactory provideCDVMFactory(Context context, String apiKey, String parkCode) {
        ParkRepository repository = getParkRepository(context);
        return new CampDetailViewModelFactory(repository, apiKey, parkCode);
    }


}
