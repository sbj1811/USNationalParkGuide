package com.sjani.usnationalparkguide.UI.Details;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Details.Campgrounds.CampgroundFragment;
import com.sjani.usnationalparkguide.UI.Details.Trails.TrailFragment;


public class FragmentSelectAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    private Uri uri;
    private String parkId;
    private int mPosition;
    private String latlong;
    private String parkCode;

    public FragmentSelectAdapter(FragmentManager fm, Context mContext, Uri uri, String parkId, int position, String latlong, String parkCode) {
        super(fm);
        this.mContext = mContext;
        this.uri = uri;
        this.parkId = parkId;
        this.mPosition = position;
        this.latlong = latlong;
        this.parkCode = parkCode;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment infoFragment = InfoFragment.newInstance(uri,parkId,mPosition,latlong);
                return infoFragment;
            case 1:
                Fragment weatherFragment = WeatherFragment.newInstance(uri,parkId,mPosition,latlong);
                return weatherFragment;
            case 2:
                Fragment trailFragment = TrailFragment.newInstance(uri,parkId,mPosition,latlong,parkCode);
                return trailFragment;
            case 3:
                Fragment campgroundFragment = CampgroundFragment.newInstance(uri,parkId,mPosition,latlong,parkCode);
                return campgroundFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.info);
            case 1:
                return mContext.getString(R.string.weather);
            case 2:
                return mContext.getString(R.string.trails);
            case 3:
                return mContext.getString(R.string.campgrounds);
            default:
                return null;
        }
    }
}
