package com.sjani.usnationalparkguide.UI.Details.Trails;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.sjani.usnationalparkguide.R;

public class TrailDetailActivity extends AppCompatActivity {

    private static final String TAG = TrailDetailActivity.class.getSimpleName();
    private static final String TRAIL_ID = "trail_id";
    private static final String URI_TRAIL = "uri_trail";
    private static final String URI = "uri";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";
    private static final String PARK_ID = "park_id";
    private static final String PARKCODE = "parkcode";
    private TrailDetailFragment trailDetailFragment;
    private TrailDetailDialogFragment trailDetailDialogFragment;

    private Uri uri;
    private Uri uriTrail;
    private String parkId;
    private String parkCode;
    private String latLong;
    private String trailId;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        uri = bundle.getParcelable(URI);
        uriTrail = bundle.getParcelable(URI_TRAIL);
        trailId = bundle.getString(TRAIL_ID);
        parkId = bundle.getString(PARK_ID);
        parkCode = bundle.getString(PARKCODE);
        latLong = bundle.getString(LATLONG);
        position = bundle.getInt(POSITION);
        if (savedInstanceState == null) {
            if (getResources().getBoolean(R.bool.dual_pane)) {
                trailDetailDialogFragment = TrailDetailDialogFragment.newInstance(uriTrail, trailId, position, parkCode, parkId, latLong, uri);
                trailDetailDialogFragment.show(getSupportFragmentManager().beginTransaction(), "Trail");
            } else {
                trailDetailFragment = (TrailDetailFragment) getSupportFragmentManager().findFragmentById(R.id.trail_detail_container);
                if (trailDetailFragment == null) {
                    trailDetailFragment = TrailDetailFragment.newInstance(uriTrail, trailId, position, parkCode, parkId, latLong, uri);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.trail_detail_container, trailDetailFragment)
                            .commit();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }
}