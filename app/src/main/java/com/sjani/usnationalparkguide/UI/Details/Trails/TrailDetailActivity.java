package com.sjani.usnationalparkguide.UI.Details.Trails;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sjani.usnationalparkguide.R;

import androidx.appcompat.app.AppCompatActivity;

public class TrailDetailActivity extends AppCompatActivity {

    private static final String TAG = TrailDetailActivity.class.getSimpleName();
    private static final String TRAIL_ID = "trail_id";
    private static final String LATLONG = "latlong";
    private static final String PARKCODE = "parkcode";
    private TrailDetailFragment trailDetailFragment;
    private TrailDetailDialogFragment trailDetailDialogFragment;
    private String parkCode;
    private String latLong;
    private String trailId;

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_detail);
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        trailId = bundle.getString(TRAIL_ID);
        parkCode = bundle.getString(PARKCODE);
        latLong = bundle.getString(LATLONG);
        if (savedInstanceState == null) {
            if (getResources().getBoolean(R.bool.dual_pane)) {
                trailDetailDialogFragment = TrailDetailDialogFragment.newInstance(trailId, parkCode, latLong);
                trailDetailDialogFragment.show(getSupportFragmentManager().beginTransaction(), "TrailEntity");
            } else {
                trailDetailFragment = (TrailDetailFragment) getSupportFragmentManager().findFragmentById(R.id.trail_detail_container);
                if (trailDetailFragment == null) {
                    trailDetailFragment = TrailDetailFragment.newInstance(trailId, parkCode, latLong);
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