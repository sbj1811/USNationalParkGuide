package com.sjani.usnationalparkguide.UI.Details;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.sjani.usnationalparkguide.R;

import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {


    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final String URI = "uri";
    private static final String PARK_ID = "park_id";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";
    private static final String PARKCODE = "parkcode";
    private static final String FROM_FAV = "from_fav";
    private boolean isFromFavNav;

    private Uri uri;
    private String parkId;
    private int position;
    private String latLong;
    private String parkCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            uri = extras.getParcelable(URI);
            parkId = extras.getString(PARK_ID);
            position = extras.getInt(POSITION);
            latLong = extras.getString(LATLONG);
            parkCode = extras.getString(PARKCODE);
            isFromFavNav = extras.getBoolean(FROM_FAV);
        } else {
            uri = savedInstanceState.getParcelable(URI);
            parkId = savedInstanceState.getString(PARK_ID);
            position = savedInstanceState.getInt(POSITION);
            latLong = savedInstanceState.getString(LATLONG);
            parkCode = savedInstanceState.getString(PARKCODE);
            isFromFavNav = savedInstanceState.getBoolean(FROM_FAV);
        }
        DetailsFragment detailsFragment = DetailsFragment.newInstance(uri, parkId, position, latLong, parkCode, isFromFavNav);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_container, detailsFragment)
                .commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(URI, uri);
        outState.putString(PARK_ID, parkId);
        outState.putInt(POSITION, position);
        outState.putString(LATLONG, latLong);
        outState.putString(PARKCODE, parkCode);
        outState.putBoolean(FROM_FAV, isFromFavNav);
        super.onSaveInstanceState(outState);
    }
}
