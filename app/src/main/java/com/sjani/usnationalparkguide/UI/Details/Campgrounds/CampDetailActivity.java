package com.sjani.usnationalparkguide.UI.Details.Campgrounds;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sjani.usnationalparkguide.R;

public class CampDetailActivity extends AppCompatActivity {

    private static final String CAMP_ID = "camp_id";
    private static final String URI = "uri";
    private static final String URI_CAMP = "uri_camp";
    private static final String POSITION = "position";
    private static final String PARK_ID = "park_id";
    private static final String PARKCODE = "parkcode";
    private static final String LATLONG = "latlong";
    private CampDetailFragment campDetailFragment;
    private CampDetailDialogFragment campDetailDialogFragment;

    private Uri uri;
    private Uri uriCamp;
    private String parkId;
    private String parkCode;
    private String latLong;
    private String campId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        uri = bundle.getParcelable(URI);
        uriCamp = bundle.getParcelable(URI_CAMP);
        campId = bundle.getString(CAMP_ID);
        position = bundle.getInt(POSITION);
        parkId = bundle.getString(PARK_ID);
        parkCode = bundle.getString(PARKCODE);
        latLong = bundle.getString(LATLONG);
        position = bundle.getInt(POSITION);
        if (savedInstanceState == null) {
            if (getResources().getBoolean(R.bool.dual_pane)) {
                campDetailDialogFragment = CampDetailDialogFragment.newInstance(uriCamp, campId, position, parkCode, parkId, latLong, uri);
                campDetailDialogFragment.show(getSupportFragmentManager(), "Camp");
            } else {
                campDetailFragment = (CampDetailFragment) getSupportFragmentManager().findFragmentById(R.id.camp_detail_container);
                if (campDetailFragment == null) {
                    campDetailFragment = CampDetailFragment.newInstance(uriCamp, campId, position, parkCode, parkId, latLong, uri);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.camp_detail_container, campDetailFragment)
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
