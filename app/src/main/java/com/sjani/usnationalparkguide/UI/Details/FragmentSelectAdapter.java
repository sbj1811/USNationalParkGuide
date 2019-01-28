package com.sjani.usnationalparkguide.UI.Details;

import android.content.Context;
import android.net.Uri;

import com.sjani.usnationalparkguide.R;
import com.sjani.usnationalparkguide.UI.Details.Alerts.AlertFragment;
import com.sjani.usnationalparkguide.UI.Details.Campgrounds.CampgroundFragment;
import com.sjani.usnationalparkguide.UI.Details.Trails.TrailFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;


public class FragmentSelectAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    private Uri uri;
    private String parkId;
    private int mPosition;
    private String latlong;
    private String parkCode;
    private boolean isFromFavNav;

    public FragmentSelectAdapter(FragmentManager fm, Context mContext, String latlong, String parkCode, boolean isFromFavNav) {
        super(fm);
        this.mContext = mContext;
        this.latlong = latlong;
        this.parkCode = parkCode;
        this.isFromFavNav = isFromFavNav;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment infoFragment = InfoFragment.newInstance(parkCode, isFromFavNav, latlong);
                return infoFragment;
            case 1:
                Fragment weatherFragment = WeatherFragment.newInstance(parkCode, isFromFavNav, latlong);
                return weatherFragment;
            case 2:
                Fragment trailFragment = TrailFragment.newInstance(parkCode, isFromFavNav, latlong);
                return trailFragment;
            case 3:
                Fragment campgroundFragment = CampgroundFragment.newInstance(parkCode, isFromFavNav, latlong);
                return campgroundFragment;
            case 4:
                Fragment alertFragment = AlertFragment.newInstance(parkCode, isFromFavNav, latlong);
                return alertFragment;
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
        return 5;
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
            case 4:
                return mContext.getString(R.string.alerts);
            default:
                return null;
        }
    }
}
