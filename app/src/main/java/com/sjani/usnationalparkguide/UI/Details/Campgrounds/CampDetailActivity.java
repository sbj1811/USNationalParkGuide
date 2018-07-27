package com.sjani.usnationalparkguide.UI.Details.Campgrounds;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sjani.usnationalparkguide.R;

public class CampDetailActivity extends AppCompatActivity {

    private static final String CAMP_ID = "camp_id";
    private static final String URI = "uri";
    private static final String POSITION = "position";
    private CampDetailFragment campDetailFragment;
    private CampDetailDialogFragment campDetailDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_details);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Uri uri = bundle.getParcelable(URI);
        String campId = bundle.getString(CAMP_ID);
        int position = bundle.getInt(POSITION);
        if (savedInstanceState == null) {
            if (getResources().getBoolean(R.bool.dual_pane)) {
                campDetailDialogFragment = CampDetailDialogFragment.newInstance(uri, campId, position);
                campDetailDialogFragment.show(getSupportFragmentManager(), "Camp");
            } else {
                campDetailFragment = (CampDetailFragment) getSupportFragmentManager().findFragmentById(R.id.camp_detail_container);
                if (campDetailFragment == null) {
                    campDetailFragment = CampDetailFragment.newInstance(uri, campId, position);
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
