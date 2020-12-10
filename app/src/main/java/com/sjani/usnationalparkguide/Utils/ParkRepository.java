package com.sjani.usnationalparkguide.Utils;


import android.util.Log;

import com.sjani.usnationalparkguide.Data.AlertDao;
import com.sjani.usnationalparkguide.Data.AlertEntity;
import com.sjani.usnationalparkguide.Data.CampDao;
import com.sjani.usnationalparkguide.Data.CampEntity;
import com.sjani.usnationalparkguide.Data.FavDao;
import com.sjani.usnationalparkguide.Data.FavParkEntity;
import com.sjani.usnationalparkguide.Data.ParkDao;
import com.sjani.usnationalparkguide.Data.ParkEntity;
import com.sjani.usnationalparkguide.Data.TrailDao;
import com.sjani.usnationalparkguide.Data.TrailEntity;
import com.sjani.usnationalparkguide.Models.Alerts.Alert;
import com.sjani.usnationalparkguide.Models.Alerts.AlertDatum;
import com.sjani.usnationalparkguide.Models.Campgrounds.CampDatum;
import com.sjani.usnationalparkguide.Models.Campgrounds.Campground;
import com.sjani.usnationalparkguide.Models.Park.Address;
import com.sjani.usnationalparkguide.Models.Park.Datum;
import com.sjani.usnationalparkguide.Models.Park.Parks;
import com.sjani.usnationalparkguide.Models.Trails.Trail;
import com.sjani.usnationalparkguide.Models.Trails.TrailDatum;
import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.Utils.NetworkSync.NPS.NPSApiConnection;
import com.sjani.usnationalparkguide.Utils.NetworkSync.NPS.NPSApiEndpointInterface;
import com.sjani.usnationalparkguide.Utils.NetworkSync.Trails.HikingprojectApiConnection;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkRepository {

    private static final String TAG = ParkRepository.class.getSimpleName();
    private ParkDao parkDao;
    private FavDao favDao;
    private TrailDao trailDao;
    private CampDao campDao;
    private AlertDao alertDao;
    private AppExecutors executor;
    private LiveData<List<Datum>> parkList;
    private LiveData<ParkEntity> park;
    private NPSApiEndpointInterface endpointInterface;
    private List<Datum> parks = new ArrayList<>();
    private List<TrailDatum> trails = new ArrayList<>();
    private List<CampDatum> camps = new ArrayList<>();
    private List<AlertDatum> alerts = new ArrayList<>();
    private LiveData<TrailEntity> trail;
    private LiveData<CampEntity> camp;
    private LiveData<AlertEntity> alert;
    private MediatorLiveData<String> mediatorFavPark = new MediatorLiveData<>();


    public ParkRepository(ParkDao parkDao, FavDao favDao, TrailDao trailDao, CampDao campDao, AlertDao alertDao, AppExecutors executor) {
        this.parkDao = parkDao;
        this.favDao = favDao;
        this.trailDao = trailDao;
        this.campDao = campDao;
        this.alertDao = alertDao;
        this.executor = executor;
    }

    public void getParksfromAPI(String apiKey, String fields, String state, String maxResults) {
        NPSApiConnection.getApi().getParks(state, apiKey, fields, maxResults).enqueue(new Callback<Parks>() {
            @Override
            public void onResponse(Call<Parks> call, Response<Parks> response) {
                Log.e(TAG, "onResponse: HERE: "+response);
                parks = response.body().getData();
                Observable.fromCallable(() -> {
                    parkDao.clearTable();
                    for (Datum p : parks) {
                        ParkEntity parkEntity = ParkTypeConverter(p);
                        parkDao.save(parkEntity);
                    }
                    return false;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();

            }

            @Override
            public void onFailure(Call<Parks> call, Throwable t) {

            }
        });
    }

    public void getTrailsFromAPI(String apiKey, String latLong) {
        StringToGPSCoordinates stringToGPSCoordinates = new StringToGPSCoordinates();
        final String gpsCoodinates[] = stringToGPSCoordinates.convertToGPS(latLong);
        HikingprojectApiConnection.getApi().getTrails(gpsCoodinates[0], gpsCoodinates[1], apiKey).enqueue(new Callback<Trail>() {
            @Override
            public void onResponse(Call<Trail> call, Response<Trail> response) {
                trails = response.body().getTrails();
                Observable.fromCallable(() -> {
                    trailDao.clearTable();
                    for (TrailDatum p : trails) {
                        TrailEntity trailEntity = TrailTypeConverter(p);
                        trailDao.save(trailEntity);
                    }
                    return false;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            }

            @Override
            public void onFailure(Call<Trail> call, Throwable t) {

            }
        });
    }

    private TrailEntity TrailTypeConverter(TrailDatum p) {
        TrailEntity trailEntity = new TrailEntity();
        trailEntity.setTrail_id(String.valueOf(p.getId()));
        trailEntity.setTrail_name(p.getName());
        trailEntity.setSummary(p.getSummary());
        trailEntity.setDifficulty(p.getDifficulty());
        trailEntity.setImage_small(p.getImgSmall());
        trailEntity.setImage_med(p.getImgMedium());
        trailEntity.setLength(String.valueOf(p.getLength()));
        trailEntity.setAscent(String.valueOf(p.getAscent()));
        trailEntity.setLatitude(String.valueOf(p.getLatitude()));
        trailEntity.setLongitude(String.valueOf(p.getLongitude()));
        trailEntity.setLocation(p.getLocation());
        trailEntity.setCondition(p.getConditionDetails());
        trailEntity.setMore_info(p.getUrl());
        return trailEntity;
    }

    public void getCampsFromAPI(String parkCode, String fields, String apiKey) {
        StringToGPSCoordinates stringToGPSCoordinates = new StringToGPSCoordinates();
        NPSApiConnection.getApi().getCampgound(parkCode, apiKey, fields).enqueue(new Callback<Campground>() {
            @Override
            public void onResponse(Call<Campground> call, Response<Campground> response) {
                Log.e(TAG, "onResponse: HERE: "+response);
                camps = response.body().getData();
                Observable.fromCallable(() -> {
                    campDao.clearTable();
                    for (CampDatum p : camps) {
                        CampEntity campEntity = CampTypeConverter(p);
                        campDao.save(campEntity);
                    }
                    return false;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            }

            @Override
            public void onFailure(Call<Campground> call, Throwable t) {

            }
        });
    }

    private CampEntity CampTypeConverter(CampDatum p) {
        CampEntity campEntity = new CampEntity();
        campEntity.setCamp_id(String.valueOf(p.getId()));
        campEntity.setCamp_name(p.getName());
        campEntity.setDescription(p.getDescription());
        campEntity.setParkCode(p.getParkCode());
        String address = String.valueOf(R.string.NA);
        if (p.getAddresses() == null || p.getAddresses().size() == 0) {
            address = "NA";
        } else {
            for (com.sjani.usnationalparkguide.Models.Campgrounds.Address addresses : p.getAddresses()) {
                if (addresses.getType().equals("Physical")) {
                    address = addresses.getLine1() + ", "
                            + (addresses.getLine3().equals("") ? "" : addresses.getLine3() + ", ")
                            + addresses.getCity() + ", "
                            + addresses.getStateCode() + " "
                            + addresses.getPostalCode();
                    break;
                } else {
                    address = addresses.getLine1() + ", "
                            + (addresses.getLine2().equals("") ? "" : addresses.getLine2() + ", ")
                            + (addresses.getLine3().equals("") ? "" : addresses.getLine3() + ", ")
                            + addresses.getCity() + ", "
                            + addresses.getStateCode() + " "
                            + addresses.getPostalCode();
                }
            }
        }
        String showers;
        if (p.getAmenities().getShowers().size() != 0) {
            showers = p.getAmenities().getShowers().get(0);
        } else {
            showers = String.valueOf(R.string.none);
        }
        campEntity.setAddress(address);
        campEntity.setLatLong(p.getLatLong());
        campEntity.setCellPhoneReception(p.getAmenities().getCellPhoneReception());
        campEntity.setShowers(showers);
        String toilets;
        if (p.getAmenities().getToilets() != null && p.getAmenities().getToilets().size() != 0) {
            toilets = p.getAmenities().getToilets().get(0);
        } else {
            toilets = String.valueOf(R.string.none);
        }
        if(p.getAmenities().getInternetConnectivity() != null) campEntity.setInternetConnectivity(p.getAmenities().getInternetConnectivity().toString());
        campEntity.setToilets(toilets);
        campEntity.setWheelchairAccess(p.getAccessibility().getWheelchairAccess());
        campEntity.setReservationsUrl(p.getReservationsUrl());
        campEntity.setDirectionsUrl(p.getDirectionsUrl());
        return campEntity;
    }

    public void getAlertsFromAPI(String parkCode, String apiKey) {
        StringToGPSCoordinates stringToGPSCoordinates = new StringToGPSCoordinates();
        NPSApiConnection.getApi().getAlerts(parkCode, apiKey).enqueue(new Callback<Alert>() {
            @Override
            public void onResponse(Call<Alert> call, Response<Alert> response) {
                Log.e(TAG, "onResponse: HERE: "+response);
                alerts = response.body().getData();
                Observable.fromCallable(() -> {
                    alertDao.clearTable();
                    for (AlertDatum p : alerts) {
                        AlertEntity alertEntity = AlertTypeConverter(p);
                        alertDao.save(alertEntity);
                    }
                    return false;
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
            }

            @Override
            public void onFailure(Call<Alert> call, Throwable t) {

            }
        });
    }

    private AlertEntity AlertTypeConverter(AlertDatum p) {
        AlertEntity alertEntity = new AlertEntity();
        alertEntity.setAlert_id(p.getId());
        alertEntity.setAlert_name(p.getTitle());
        alertEntity.setDescription(p.getDescription());
        alertEntity.setCategory(p.getCategory());
        alertEntity.setParkCode(p.getParkCode());
        return alertEntity;
    }

    public LiveData<List<ParkEntity>> getParkListFromDb(String apiKey, String fields, String state, String maxResults) {
        getParksfromAPI(apiKey, fields, state, maxResults);
        return parkDao.getAllParks();
    }

    public LiveData<List<TrailEntity>> getTrailListFromDb(String apiKey, String latLong) {
        getTrailsFromAPI(apiKey, latLong);
        return trailDao.getAllTrails();
    }

    public LiveData<List<CampEntity>> getCampListFromDb(String parkCode, String fields, String apiKey) {
        getCampsFromAPI(parkCode, fields, apiKey);
        return campDao.getAllCamps();
    }

    public LiveData<List<AlertEntity>> getAlertListFromDb(String parkCode, String apiKey) {
        getAlertsFromAPI(parkCode, apiKey);
        return alertDao.getAllAlerts();
    }

    public LiveData<ParkEntity> getParkFromDb(String parkCode) {
        park = parkDao.getPark(parkCode);
        return park;
    }

    public LiveData<TrailEntity> getTrailFromDb(String trailId) {
        trail = trailDao.getTrail(trailId);
        return trail;
    }

    public LiveData<CampEntity> getCampFromDb(String campId) {
        camp = campDao.getCamp(campId);
        return camp;
    }

    public void updateFav(FavParkEntity park) {
        favDao.clearTable();
        favDao.save(park);
    }

    public void clearFav() {
        favDao.clearTable();
    }

    public LiveData<List<FavParkEntity>> getFavPark() {
        return favDao.getFavPark();
    }


    private ParkEntity ParkTypeConverter(Datum p) {
        ParkEntity parkEntity = new ParkEntity();
        parkEntity.setPark_id(p.getId());
        parkEntity.setLatLong(p.getLatLong());
        parkEntity.setDescription(p.getDescription());
        parkEntity.setDesignation(p.getDesignation());
        parkEntity.setPark_name(p.getFullName());
        parkEntity.setParkCode(p.getParkCode());
        parkEntity.setStates(p.getStates());
        String address = String.valueOf(R.string.NA);
        if (p.getAddresses() == null || p.getAddresses().size() == 0) {
            address = String.valueOf(R.string.NA);
        } else {
            for (Address addresses : p.getAddresses()) {
                if (addresses.getType().equals("Physical")) {
                    address = addresses.getLine1() + ", "
                            + (addresses.getLine3().equals("") ? "" : addresses.getLine3() + ", ")
                            + addresses.getCity() + ", "
                            + addresses.getStateCode() + " "
                            + addresses.getPostalCode();
                    break;
                } else {
                    address = addresses.getLine1() + ", "
                            + (addresses.getLine2().equals("") ? "" : addresses.getLine2() + ", ")
                            + (addresses.getLine3().equals("") ? "" : addresses.getLine3() + ", ")
                            + addresses.getCity() + ", "
                            + addresses.getStateCode() + " "
                            + addresses.getPostalCode();
                }
            }
        }
        parkEntity.setAddress(address);
        String image = "";
        if (p.getImages() != null) {
            if (p.getImages().size() == 0) {
                image = "";
            } else {
                image = p.getImages().get(0).getUrl();
            }
        } else {
            image = "";
        }
        String phone = String.valueOf(R.string.NA);
        ;
        String email = String.valueOf(R.string.NA);
        ;
        if (p.getContacts() == null) {
            phone = String.valueOf(R.string.NA);
            email = String.valueOf(R.string.NA);
        } else {
            if(p.getContacts().getPhoneNumbers() == null || p.getContacts().getPhoneNumbers().size()==0){
                phone = String.valueOf(R.string.NA);
            } else {
                phone = p.getContacts().getPhoneNumbers().get(0).getPhoneNumber();
            }
            if(p.getContacts().getEmailAddresses() == null || p.getContacts().getEmailAddresses().size()==0){
                email = String.valueOf(R.string.NA);
            } else {
                email = p.getContacts().getEmailAddresses().get(0).getEmailAddress();
            }
        }
        parkEntity.setImage(image);
        parkEntity.setPhone(phone);
        parkEntity.setEmail(email);

        return parkEntity;
    }


}
