package com.sjani.usnationalparkguide.UI.Details.Trails;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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
        parkId =bundle.getString(PARK_ID);
        parkCode = bundle.getString(PARKCODE);
        latLong = bundle.getString(LATLONG);
        position = bundle.getInt(POSITION);
        if(getResources().getBoolean(R.bool.dual_pane)){
            trailDetailDialogFragment = TrailDetailDialogFragment.newInstance(uriTrail, trailId, position, parkCode, parkId, latLong, uri);
            trailDetailDialogFragment.show(getSupportFragmentManager(),"Trail");
        } else {
            trailDetailFragment = (TrailDetailFragment) getSupportFragmentManager().findFragmentById(R.id.trail_detail_container);
            if (trailDetailFragment == null) {
                trailDetailFragment = TrailDetailFragment.newInstance(uriTrail, trailId, position, parkCode, parkId, latLong, uri);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.trail_detail_container, trailDetailFragment)
                        .commit();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.share_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // action with ID action_refresh was selected
//            case R.id.action_share:
//                Toast.makeText(this, "Share selected", Toast.LENGTH_SHORT)
//                        .show();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
