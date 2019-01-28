package com.sjani.usnationalparkguide.UI.Details;

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
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {


    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final String PARKCODE = "parkcode";
    private static final String FROM_FAV = "from_fav";
    private static final String LATLONG = "latlong";
    private boolean isFromFavNav;

    private String parkCode;
    private String latLong;
    private DetailsFragment detailsFragment;

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
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
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
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            parkCode = extras.getString(PARKCODE);
            latLong = extras.getString(LATLONG);
            isFromFavNav = extras.getBoolean(FROM_FAV);
        } else {
            parkCode = savedInstanceState.getString(PARKCODE);
            latLong = savedInstanceState.getString(LATLONG);
            isFromFavNav = savedInstanceState.getBoolean(FROM_FAV);
        }
        if (savedInstanceState == null) {
            detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.list_container);
            if (detailsFragment == null) {
                detailsFragment = DetailsFragment.newInstance(parkCode, latLong, isFromFavNav);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.details_container, detailsFragment)
                        .commit();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ;
        outState.putString(PARKCODE, parkCode);
        outState.putString(LATLONG, latLong);
        outState.putBoolean(FROM_FAV, isFromFavNav);
        super.onSaveInstanceState(outState);
    }
}