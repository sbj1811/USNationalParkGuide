package com.example.android.usnationalparkguide.UI.Details;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.bumptech.glide.Glide;
import com.example.android.usnationalparkguide.Data.ParkContract;
import com.example.android.usnationalparkguide.R;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.detail_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.detail_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.detail_photo)
    SimpleDraweeView parkImageView;

    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static final String URI = "uri";
    private static final String PARK_ID = "park_id";
    private static final String POSITION = "position";
    private static final String LATLONG = "latlong";
    private static final int LOADER_ID = 2;

    private Uri uri;
    private String parkId;
    private int position;
    private String latLong;
    private Cursor cursor;

    private static final String[] PROJECTION = new String[]{
            ParkContract.ParkEntry._ID,
            ParkContract.ParkEntry.COLUMN_PARK_ID,
            ParkContract.ParkEntry.COLUMN_PARK_NAME,
            ParkContract.ParkEntry.COLUMN_PARK_STATES,
            ParkContract.ParkEntry.COLUMN_PARK_CODE,
            ParkContract.ParkEntry.COLUMN_PARK_LATLONG,
            ParkContract.ParkEntry.COLUMN_PARK_DESCRIPTION,
            ParkContract.ParkEntry.COLUMN_PARK_DESIGNATION,
            ParkContract.ParkEntry.COLUMN_PARK_ADDRESS,
            ParkContract.ParkEntry.COLUMN_PARK_PHONE,
            ParkContract.ParkEntry.COLUMN_PARK_EMAIL,
            ParkContract.ParkEntry.COLUMN_PARK_IMAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        uri = extras.getParcelable(URI);
        parkId = extras.getString(PARK_ID);
        position = extras.getInt(POSITION);
        latLong = extras.getString(LATLONG);
        getSupportLoaderManager().initLoader(LOADER_ID,null,this);
        FragmentSelectAdapter selectAdapter = new FragmentSelectAdapter(getSupportFragmentManager(),this,uri,parkId,position,latLong);
        mViewPager.setAdapter(selectAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, uri, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        cursor = data;
        cursor.moveToPosition(position);
        String imageUrl = cursor.getString(cursor.getColumnIndex(ParkContract.ParkEntry.COLUMN_PARK_IMAGE));
        Glide.with(parkImageView.getContext())
                .load(imageUrl)
                .fitCenter()
                .into(parkImageView);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        cursor = null;
    }
}
