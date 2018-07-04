package com.example.android.usnationalparkguide.UI.Details;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.usnationalparkguide.R;

public class FragmentSelectAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private Uri uri;
    private String parkId;
    private int mPosition;
    private String latlong;

    public FragmentSelectAdapter(FragmentManager fm, Context mContext, Uri uri, String parkId, int position, String latlong) {
        super(fm);
        this.mContext = mContext;
        this.uri = uri;
        this.parkId = parkId;
        this.mPosition = position;
        this.latlong = latlong;
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
//            case 2:
//                return mContext.getString(R.string.trails);
//            case 3:
//                return mContext.getString(R.string.campgrounds);
//            case 4:
//                return mContext.getString(R.string.alerts);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.info);
            case 1:
                return mContext.getString(R.string.weather);
//            case 2:
//                return mContext.getString(R.string.trails);
//            case 3:
//                return mContext.getString(R.string.campgrounds);
//            case 4:
//                return mContext.getString(R.string.alerts);
            default:
                return null;
        }
    }
}
