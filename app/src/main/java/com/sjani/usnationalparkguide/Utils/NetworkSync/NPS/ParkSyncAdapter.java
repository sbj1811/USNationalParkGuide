package com.sjani.usnationalparkguide.Utils.NetworkSync.NPS;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.sjani.usnationalparkguide.Data.CampContract;
import com.sjani.usnationalparkguide.Data.ParkContract;
import com.sjani.usnationalparkguide.Models.Park.Address;
import com.sjani.usnationalparkguide.Models.Park.Datum;
import com.sjani.usnationalparkguide.Models.Park.Parks;
import com.sjani.usnationalparkguide.R;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ParkSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = ParkSyncAdapter.class.getSimpleName();
    private static final String SELECTED_STATE = "selected_state";
    private static final String MAX_RESULTS = "max_results";
    private final ContentResolver contentResolver;
    private String apiKey;
    private String fields;
    private List<Datum> parkList;
    private Context context;


    public ParkSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        this.context = context;
        contentResolver = context.getContentResolver();
    }

    private static List<Datum> loadParkData(String apiKey, String fields, String state, String maxResults) throws IOException {
        String selectedState;
        if (state == null) {
            selectedState = "AL";
        } else {
            selectedState = state;
        }
        Call<Parks> parkData = NPSApiConnection.getApi().getParks(selectedState, apiKey, fields, maxResults);
        Response<Parks> response = parkData.execute();
        List<Datum> parkList = response.body().getData();
        return parkList;
    }

    public static void performSync(String mState, String maxResults) {
        Bundle b = new Bundle();
        b.putString(SELECTED_STATE, mState);
        b.putString(MAX_RESULTS, maxResults);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(AccountModel.getAccount(),
                ParkContract.CONTENT_AUTHORITY, b);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

        apiKey = getContext().getResources().getString(R.string.NPSapiKey);
        fields = getContext().getResources().getString(R.string.fields);
        String selectedState = bundle.getString(SELECTED_STATE);
        String maxResults = bundle.getString(MAX_RESULTS);
        try {
            parkList = loadParkData(apiKey, fields, selectedState, maxResults);
            ContentValues[] parkContent = makeContentFromParkList(parkList);
            contentResolver.delete(ParkContract.ParkEntry.CONTENT_URI_PARKS, null, null);
            contentResolver.bulkInsert(ParkContract.ParkEntry.CONTENT_URI_PARKS, parkContent);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public ContentValues[] makeContentFromParkList(List<Datum> list) {

        if (list == null) {
            return null;
        }
        ContentValues[] result = new ContentValues[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Datum data = list.get(i);
            ContentValues parkValues = new ContentValues();
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_ID, data.getId());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_NAME, data.getFullName());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_STATES, data.getStates());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_CODE, data.getParkCode());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_LATLONG, data.getLatLong());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_DESCRIPTION, data.getDescription());
            parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_DESIGNATION, data.getDesignation());
            String address = "NA";
            if (data.getAddresses() == null || data.getAddresses().size() == 0) {
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_ADDRESS, String.valueOf(R.string.NA));
            } else {
                for (Address addresses : data.getAddresses()) {
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

                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_ADDRESS, address);
            }

            if (data.getContacts() == null) {
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_PHONE, context.getResources().getString(R.string.na));
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_EMAIL, context.getResources().getString(R.string.na));
            } else {
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_PHONE, data.getContacts().getPhoneNumbers().get(0).getPhoneNumber());
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_EMAIL, data.getContacts().getEmailAddresses().get(0).getEmailAddress());
            }
            if (data.getImages().size() == 0) {
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_IMAGE, "");
            } else {
                parkValues.put(ParkContract.ParkEntry.COLUMN_PARK_IMAGE, data.getImages().get(0).getUrl());
            }
            result[i] = parkValues;
        }

        return result;
    }

}
