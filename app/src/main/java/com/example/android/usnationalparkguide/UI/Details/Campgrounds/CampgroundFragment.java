package com.example.android.usnationalparkguide.UI.Details.Campgrounds;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.usnationalparkguide.Data.CampContract;
import com.example.android.usnationalparkguide.Models.Campgrounds.Address;
import com.example.android.usnationalparkguide.Models.Campgrounds.CampDatum;
import com.example.android.usnationalparkguide.Models.Campgrounds.Campground;
import com.example.android.usnationalparkguide.R;
import com.example.android.usnationalparkguide.Utils.Listeners.OnListFragmentInteractionListener;
import com.example.android.usnationalparkguide.Utils.NetworkSync.NPS.NPSApiConnection;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class CampgroundFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, OnListFragmentInteractionListener {

    private static final String TAG = CampgroundFragment.class.getSimpleName();
    private static final String CAMP_ID = "camp_id";
    private static final String URI = "uri";
    private static final String URI_CAMP = "uri_camp";
    private static final String POSITION = "position";
    private static final String PARK_ID = "park_id";
    private static final String PARKCODE = "parkcode";
    private static final String LATLONG = "latlong";
    private static final int LOADER_ID = 5;
    private String parkCode;
    private Uri uri;
    private String parkId;
    private String latLong;
    private CampgroundRecyclerViewAdapter adapter;
    private List<CampDatum> camps;

    @BindView(R.id.rv_camp)
    RecyclerView recyclerView;


    private static final String[] PROJECTION = new String[]{
            CampContract.CampEntry._ID,
            CampContract.CampEntry.COLUMN_CAMP_ID,
            CampContract.CampEntry.COLUMN_CAMP_NAME,
            CampContract.CampEntry.COLUMN_CAMP_DESCRIPTION,
            CampContract.CampEntry.COLUMN_CAMP_PARKCODE,
            CampContract.CampEntry.COLUMN_CAMP_ADDRESSS,
            CampContract.CampEntry.COLUMN_CAMP_LATLONG,
            CampContract.CampEntry.COLUMN_CAMP_CELLRECEP,
            CampContract.CampEntry.COLUMN_CAMP_SHOWERS,
            CampContract.CampEntry.COLUMN_CAMP_INTERNET,
            CampContract.CampEntry.COLUMN_CAMP_TOILET,
            CampContract.CampEntry.COLUMN_CAMP_WHEELCHAIR,
            CampContract.CampEntry.COLUMN_CAMP_RESERVURL,
            CampContract.CampEntry.COLUMN_CAMP_DIRECTIONURL
    };

    public CampgroundFragment() {
    }

    public static CampgroundFragment newInstance(Uri uri, String parkId, int position, String latlong, String parkCode) {
        CampgroundFragment fragment = new CampgroundFragment();
        Bundle args = new Bundle();
        args.putParcelable(URI, uri);
        args.putString(PARK_ID, parkId);
        args.putInt(POSITION,position);
        args.putString(LATLONG,latlong);
        args.putString(PARKCODE,parkCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_campgound_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new CampgroundRecyclerViewAdapter(this,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            latLong = getArguments().getString(LATLONG);
            parkId = getArguments().getString(PARK_ID);
            parkCode = getArguments().getString(PARKCODE);
            uri = getArguments().getParcelable(URI);
        }
        new NetworkCall().execute();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        uri = CampContract.CampEntry.CONTENT_URI_CAMP;
        return new android.support.v4.content.CursorLoader(getActivity(),uri,PROJECTION,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (adapter != null) {
            adapter.swapCursor(null);
        }
    }

    @Override
    public void onListFragmentInteraction(String id, int position) {

        Uri uriCamp = CampContract.CampEntry.CONTENT_URI_CAMP;
        Intent intent = new Intent(getActivity(),CampDetailActivity.class);
        intent.putExtra(URI,uri);
        intent.putExtra(URI_CAMP,uriCamp);
        intent.putExtra(PARK_ID,parkId);
        intent.putExtra(PARKCODE,parkCode);
        intent.putExtra(LATLONG,latLong);
        intent.putExtra(CAMP_ID,id);
        intent.putExtra(POSITION,position);
        startActivity(intent);

    }


    private class NetworkCall extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            camps = loadCampData();
            Log.e(TAG, "doInBackground: HERE");
            ContentValues[] campContent = makeContentFromCampList(camps);
            ContentResolver contentResolver =  getContext().getContentResolver();
            contentResolver.delete(CampContract.CampEntry.CONTENT_URI_CAMP,null,null);
            contentResolver.bulkInsert(CampContract.CampEntry.CONTENT_URI_CAMP,campContent);
            return null;
        }


    }

    private List<CampDatum>  loadCampData() {
        String apiKey = getContext().getResources().getString(R.string.NPSapiKey);
        String feilds = getContext().getResources().getString(R.string.fields_cg);
        Call<Campground> campData = NPSApiConnection.getApi().getCampgound(parkCode,apiKey,feilds);
        Response<Campground> response = null;
        try {
            response = campData.execute();
            Log.e(TAG, "loadCampData: "+response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "loadCampData: HERE"+response.body());
        return response.body().getData();
    }

    public static ContentValues[] makeContentFromCampList(List<CampDatum> list) {
        Log.e(TAG, "makeContentFromCampList: HERE: "+list);
        if (list == null) {
            Log.e(TAG, "makeContentFromCampList: LIST NULL");
            return null;
        }
        ContentValues[] result = new ContentValues[list.size()];

        for (int i = 0; i < list.size(); i++) {
            CampDatum data = list.get(i);
            Log.e(TAG, "makeContentFromCampList: HERE: "+data);
            ContentValues campValues = new ContentValues();
            campValues.put(CampContract.CampEntry.COLUMN_CAMP_ID, data.getId());
            campValues.put(CampContract.CampEntry.COLUMN_CAMP_NAME, data.getName());
            campValues.put(CampContract.CampEntry.COLUMN_CAMP_DESCRIPTION, data.getDescription());
            campValues.put(CampContract.CampEntry.COLUMN_CAMP_PARKCODE, data.getParkCode());
            String address = "NA";
            if(data.getAddresses() == null || data.getAddresses().size() == 0) {
                campValues.put(CampContract.CampEntry.COLUMN_CAMP_ADDRESSS, String.valueOf(R.string.NA));
            } else {
                for (Address addresses: data.getAddresses()) {
                    if (addresses.getType().equals("Physical")) {
                        address = addresses.getLine1()+", "
                                +(addresses.getLine3().equals("")?"":addresses.getLine3()+", ")
                                +addresses.getCity()+", "
                                +addresses.getStateCode()+" "
                                +addresses.getPostalCode();
                        break;
                    } else {
                        address = addresses.getLine1()+", "
                                +(addresses.getLine2().equals("")?"":addresses.getLine2()+", ")
                                +(addresses.getLine3().equals("")?"":addresses.getLine3()+", ")
                                +addresses.getCity()+", "
                                +addresses.getStateCode()+" "
                                +addresses.getPostalCode();
                    }
                }

                campValues.put(CampContract.CampEntry.COLUMN_CAMP_ADDRESSS, address);
            }
            campValues.put(CampContract.CampEntry.COLUMN_CAMP_LATLONG, data.getLatLong());
            campValues.put(CampContract.CampEntry.COLUMN_CAMP_CELLRECEP, data.getAmenities().getCellPhoneReception());
            if(data.getAmenities().getShowers().size() != 0) {
                campValues.put(CampContract.CampEntry.COLUMN_CAMP_SHOWERS, data.getAmenities().getShowers().get(0));
            } else {
                campValues.put(CampContract.CampEntry.COLUMN_CAMP_SHOWERS, "None");
            }
            campValues.put(CampContract.CampEntry.COLUMN_CAMP_INTERNET, data.getAmenities().getInternetConnectivity().toString());
            if (data.getAmenities().getToilets().size() != 0) {
                campValues.put(CampContract.CampEntry.COLUMN_CAMP_TOILET, data.getAmenities().getToilets().get(0));
            } else {
                campValues.put(CampContract.CampEntry.COLUMN_CAMP_TOILET, "None");
            }
            campValues.put(CampContract.CampEntry.COLUMN_CAMP_WHEELCHAIR, data.getAccessibility().getWheelchairAccess());
            campValues.put(CampContract.CampEntry.COLUMN_CAMP_RESERVURL, data.getReservationsUrl());
            campValues.put(CampContract.CampEntry.COLUMN_CAMP_DIRECTIONURL, data.getDirectionsUrl());
            result[i] = campValues;
        }
        return result;
    }
}
