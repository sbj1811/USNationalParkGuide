package com.sjani.usnationalparkguide.UI.Details.Campgrounds;

import android.content.Intent;
import android.os.Bundle;

import com.sjani.usnationalparkguide.R;

import androidx.appcompat.app.AppCompatActivity;

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

    private String parkCode;
    private String campId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        campId = bundle.getString(CAMP_ID);
        parkCode = bundle.getString(PARKCODE);
        if (savedInstanceState == null) {
            if (getResources().getBoolean(R.bool.dual_pane)) {
                campDetailDialogFragment = CampDetailDialogFragment.newInstance(campId, parkCode);
                campDetailDialogFragment.show(getSupportFragmentManager(), "CampEntity");
            } else {
                campDetailFragment = (CampDetailFragment) getSupportFragmentManager().findFragmentById(R.id.camp_detail_container);
                if (campDetailFragment == null) {
                    campDetailFragment = CampDetailFragment.newInstance(campId, parkCode);
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
