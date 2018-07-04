package com.example.android.usnationalparkguide.Utils.NetworkSync.NPS;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.android.usnationalparkguide.Data.ParkContract;
import com.example.android.usnationalparkguide.Models.Park.Datum;
import com.example.android.usnationalparkguide.Models.Park.Parks;
import com.example.android.usnationalparkguide.R;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ParkSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = ParkSyncAdapter.class.getSimpleName();
    private static final String SELECTED_STATE = "selected_state";
    private String apiKey;
    private String fields;
    private List<Datum> parkList;
    private final ContentResolver contentResolver;


    public ParkSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        contentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

        apiKey = getContext().getResources().getString(R.string.NPSapiKey);
        fields = getContext().getResources().getString(R.string.fields);
        String selectedState = bundle.getString(SELECTED_STATE);
        try {
            parkList = loadParkData(apiKey,fields,selectedState);
            ContentValues[] parkContent = makeContentFromParkList(parkList);
            contentResolver.delete(ParkContract.ParkEntry.CONTENT_URI_PARKS,null,null);
            contentResolver.bulkInsert(ParkContract.ParkEntry.CONTENT_URI_PARKS,parkContent);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static List<Datum> loadParkData (String apiKey, String fields, String state) throws IOException {
        Call<Parks> parkData = NPSApiConnection.getApi().getParks(state,apiKey,fields);
        Response<Parks> response = parkData.execute();
        List<Datum> parkList = response.body().getData();
        return parkList;
    }

    private void getFavmovies(){
        List<Datum> movies;
        Cursor cursor;

        cursor = getContext().getContentResolver().query(ParkContract.ParkEntry.CONTENT_URI_FAVORITES,
                null,
                null,
                null, null);

    }

    public static ContentValues[] makeContentFromParkList(List<Datum> list) {

        if (list == null) {
            return null;
        }
        ContentValues[] result = new ContentValues[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Datum data = list.get(i);
            ContentValues parkValues = new ContentValues();
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_ID, data.getId());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_NAME, data.getName());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_STATES, data.getStates());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_CODE, data.getParkCode());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_LATLONG, data.getLatLong());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_DESCRIPTION, data.getDescription());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_DESIGNATION, data.getDesignation());
            String address;
            if(data.getAddresses().size() == 0) {
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_ADDRESS, String.valueOf(R.string.NA));
            } else {
                if (data.getAddresses().get(0).getType().equals("Physical")) {
                    address = data.getAddresses().get(0).getLine1()+", "
                            +(data.getAddresses().get(0).getLine2().equals("")?"":data.getAddresses().get(0).getLine2()+", ")
                            +(data.getAddresses().get(0).getLine3().equals("")?"":data.getAddresses().get(0).getLine3()+", ")
                            +data.getAddresses().get(0).getCity()+", "
                            +data.getAddresses().get(0).getStateCode()+" "
                            +data.getAddresses().get(0).getPostalCode();
                } else {
                    address = data.getAddresses().get(1).getLine1()+", "
                            +(data.getAddresses().get(1).getLine2().equals("")?"":data.getAddresses().get(1).getLine2()+", ")
                            +(data.getAddresses().get(1).getLine3().equals("")?"":data.getAddresses().get(1).getLine3()+", ")
                            +data.getAddresses().get(1).getCity()+", "
                            +data.getAddresses().get(1).getStateCode()+" "
                            +data.getAddresses().get(1).getPostalCode();
                }
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_ADDRESS, address);
            }
            if(data.getContacts() == null) {
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_PHONE, String.valueOf(R.string.NA));
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_EMAIL, String.valueOf(R.string.NA));
            } else {
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_PHONE, data.getContacts().getPhoneNumbers().get(0).getPhoneNumber());
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_EMAIL, data.getContacts().getEmailAddresses().get(0).getEmailAddress());
            }
            if(data.getImages().size() == 0) {
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_IMAGE, String.valueOf(R.drawable.empty_detail));
            } else {
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_IMAGE, data.getImages().get(0).getUrl());
            }
            result[i] = parkValues;
        }

        return result;
    }

    public static void performSync(String mState) {
        Bundle b = new Bundle();
        b.putString(SELECTED_STATE,mState);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(AccountModel.getAccount(),
                ParkContract.CONTENT_AUTHORITY, b);
    }

}
